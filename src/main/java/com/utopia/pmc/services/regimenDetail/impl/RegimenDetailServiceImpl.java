package com.utopia.pmc.services.regimenDetail.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.statuses.NotificationStatus;
import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.database.DailyData;
import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.request.regimendetail.RegimenDetailRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotificationResponse;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.data.entities.Medicine;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.data.repositories.MedicineRepository;
import com.utopia.pmc.data.repositories.RegimenDetailRepository;
import com.utopia.pmc.data.repositories.RegimenRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.NotFoundException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.RegimenDetailMapper;
import com.utopia.pmc.mappers.RegimenMapper;
import com.utopia.pmc.services.payment.PaymentPlansService;
import com.utopia.pmc.services.regimenDetail.RegimenDetailService;
import com.utopia.pmc.utils.RegimenFunction;

import lombok.Builder;

@Service
@Builder
public class RegimenDetailServiceImpl implements RegimenDetailService {
    @Autowired
    private RegimenDetailRepository regimentDetailRepository;
    @Autowired
    private RegimenRepository regimentRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private PaymentPlansService paymentPlansService;
    @Autowired
    private RegimenDetailMapper regimentDetailMapper;
    @Autowired
    private Message message;
    @Autowired
    private RegimenFunction regimenFunction;

    @Override
    @Transactional
    public void createRegimentDetails(RegimenRequest regimentRequest) {
        Map<Long, Integer> medicineRequests = new HashMap<>();
        Map<Long, RegimenDetailRequest> regimentDetailRequetsMap = new HashMap<>();
        Long regimentId = regimentRequest.getId();
        Optional<Regimen> regimentOtp = regimentRepository.findById(regimentId);

        if (regimentOtp.isEmpty()) {
            throw new BadRequestException(message.objectNotFoundByIdMessage("Regiment", regimentId));
        }

        Regimen regiment = regimentOtp.get();
        paymentPlansService.checkUserPlan(regimentOtp.get().getUser());

        for (RegimenDetailRequest regimentDetailRequest : regimentRequest.getRegimentDetailRequests()) {
            if (medicineRequests.containsKey(regimentDetailRequest.getMedicineId())) {
                throw new BadRequestException("Duplicate medicine " + regimentDetailRequest.getMedicineId());
            }
            regimentDetailRequetsMap.put(regimentDetailRequest.getMedicineId(), regimentDetailRequest);
            medicineRequests.put(regimentDetailRequest.getMedicineId(), regimentDetailRequest.getTakenQuantity());
        }

        List<Medicine> medicines = medicineRepository.findByIdIn(medicineRequests.keySet());
        if (medicines.size() < medicineRequests.size()) {
            throw new BadRequestException("Some demicine not exist.");
        }

        List<RegimenDetail> regimentDetails = new ArrayList<>();
        for (Medicine medicine : medicines) {
            Long medicineId = medicine.getId();
            int takenQuantity = medicineRequests.get(medicineId);
            RegimenDetailRequest regimentDetailRequest = regimentDetailRequetsMap.get(medicineId);
            RegimenDetail regimentDetail = regimentDetailMapper.mapDtoToEntity(regimentDetailRequest);
            Integer totalMedicine = regimenFunction.calculateMedicineQuantity(takenQuantity, regiment.getDoseRegiment(),
                    regiment.getPeriod());

            regimentDetail.setNumberOfMedicine(totalMedicine);
            regimentDetail.setTakenQuantity(takenQuantity);
            regimentDetail.setMedicine(medicine);
            regimentDetail.setRegimen(regimentOtp.get());

            regimentDetails.add(regimentDetail);
        }
        regimentDetailRepository.saveAll(regimentDetails);

        regiment.setRegimentDetails(regimentDetails);
        regimentRepository.save(regiment);
    }

    @Override
    public Map<String, List<RegimenDetailResponse>> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime startTime, LocalTime endTime) {
        List<RegimenDetail> regimentDetails = regimentDetailRepository.findByStatusAndTime(
                regimentStatus,
                startTime, endTime);

        if (regimentDetails.isEmpty()) {
            throw new BadRequestException(message.emptyList("Regiment"));
        }

        Map<String, List<RegimenDetailResponse>> result = new HashMap<>();

        for (RegimenDetail regimentDetail : regimentDetails) {
            Regimen regiment = regimentDetail.getRegimen();
            String deviceToken = regiment.getDeviceToken();

            List<RegimenDetailResponse> regimenDetailResponses = result.getOrDefault(deviceToken, new ArrayList<>());
            RegimenDetailResponse regimenDetailResponse = regimentDetailMapper.mapEntityToDto(regimentDetail);
            regimenDetailResponses.add(regimenDetailResponse);

            result.put(deviceToken, regimenDetailResponses);
        }

        return result;
    }

    @Override
    public List<RegimenDetailResponse> getRegimenDetailResponse(Long regimenId) {
        List<RegimenDetail> regimenDetailResponses = regimentDetailRepository.findByRegimenId(regimenId);
        if (regimenDetailResponses.isEmpty()) {
            throw new NotFoundException(message.emptyList("Regimen details"));
        }
        return regimentDetailMapper.mapEntityToDtos(regimenDetailResponses);
    }

}
