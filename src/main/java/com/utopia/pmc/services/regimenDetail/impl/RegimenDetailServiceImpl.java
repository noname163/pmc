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

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.request.regimendetail.RegimenDetailRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotifiactionResponse;
import com.utopia.pmc.data.entities.Medicine;
import com.utopia.pmc.data.entities.Regiment;
import com.utopia.pmc.data.entities.RegimentDetail;
import com.utopia.pmc.data.repositories.MedicineRepository;
import com.utopia.pmc.data.repositories.RegimentDetailRepository;
import com.utopia.pmc.data.repositories.RegimentRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.EmptyException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.RegimenDetailMapper;
import com.utopia.pmc.services.payment.PaymentPlansService;
import com.utopia.pmc.services.regimenDetail.RegimenDetailService;
import com.utopia.pmc.utils.RegimenFunction;

@Service
public class RegimenDetailServiceImpl implements RegimenDetailService {
    @Autowired
    private RegimentDetailRepository regimentDetailRepository;
    @Autowired
    private RegimentRepository regimentRepository;
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
        Optional<Regiment> regimentOtp = regimentRepository.findById(regimentId);

        if (regimentOtp.isEmpty()) {
            throw new BadRequestException(message.objectNotFoundByIdMessage("Regiment", regimentId));
        }

        Regiment regiment = regimentOtp.get();
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

        List<RegimentDetail> regimentDetails = new ArrayList<>();
        for (Medicine medicine : medicines) {
            Long medicineId = medicine.getId();
            int takenQuantity = medicineRequests.get(medicineId);
            RegimenDetailRequest regimentDetailRequest = regimentDetailRequetsMap.get(medicineId);
            RegimentDetail regimentDetail = regimentDetailMapper.mapDtoToEntity(regimentDetailRequest);
            Integer totalMedicine = regimenFunction.calculateMedicineQuantity(takenQuantity, regiment.getDoseRegiment(), regiment.getPeriod());
            
            regimentDetail.setNumberOfMedicine(totalMedicine);
            regimentDetail.setTakenQuantity(takenQuantity);
            regimentDetail.setMedicine(medicine);
            regimentDetail.setRegiment(regimentOtp.get());

            regimentDetails.add(regimentDetail);
        }
        regimentDetailRepository.saveAll(regimentDetails);
        
        regiment.setRegimentDetails(regimentDetails);
        regimentRepository.save(regiment);
    }

    @Override
    public Map<Long, RegimenNotifiactionResponse> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime startTime, LocalTime endTime) {

        List<RegimentDetail> regimentDetails = regimentDetailRepository.findByStatusAndTime(
                regimentStatus,
                startTime, endTime);

        if (regimentDetails.isEmpty()) {
            throw new BadRequestException(message.emptyList("Regiment"));
        }
        Map<Long, RegimenNotifiactionResponse> result = new HashMap<>();
        for (RegimentDetail regimentDetail : regimentDetails) {
            Regiment regiment = regimentDetail.getRegiment();

            RegimenNotifiactionResponse notificationResponse = result.get(regiment.getId());
            if (notificationResponse == null) {
                notificationResponse = RegimenNotifiactionResponse.builder()
                        .regimentName(regiment.getName())
                        .regimentImage(regiment.getImage())
                        .doseRegiment(regiment.getDoseRegiment())
                        .userDeviceToken(regiment.getDeviceToken())
                        .regimentId(regiment.getId())
                        .takenTime(regimenFunction.determineTakenTime(regimentDetail).toString())
                        .build();
                result.put(regiment.getId(), notificationResponse);
            }
            result.put(regiment.getId(), notificationResponse);
        }

        return result;
    }

}
