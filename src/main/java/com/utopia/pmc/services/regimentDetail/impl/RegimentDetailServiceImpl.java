package com.utopia.pmc.services.regimentDetail.impl;

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
import com.utopia.pmc.data.dto.request.regiment.RegimentRequest;
import com.utopia.pmc.data.dto.request.regimentdetail.RegimentDetailRequest;
import com.utopia.pmc.data.dto.response.regiment.RegimentNotifiactionResponse;
import com.utopia.pmc.data.entities.Medicine;
import com.utopia.pmc.data.entities.Regiment;
import com.utopia.pmc.data.entities.RegimentDetail;
import com.utopia.pmc.data.repositories.MedicineRepository;
import com.utopia.pmc.data.repositories.RegimentDetailRepository;
import com.utopia.pmc.data.repositories.RegimentRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.EmptyException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.RegimentDetailMapper;
import com.utopia.pmc.services.regimentDetail.RegimentDetailService;
import com.utopia.pmc.utils.DetermineTakenTime;

@Service
public class RegimentDetailServiceImpl implements RegimentDetailService {
    @Autowired
    private RegimentDetailRepository regimentDetailRepository;
    @Autowired
    private RegimentRepository regimentRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private RegimentDetailMapper regimentDetailMapper;
    @Autowired
    private Message message;
    @Autowired
    private DetermineTakenTime determineTakenTime;

    @Override
    @Transactional
    public void createRegimentDetails(RegimentRequest regimentRequest) {
        Map<Long, Integer> medicineRequests = new HashMap<>();
        Map<Long, RegimentDetailRequest> regimentDetailRequetsMap = new HashMap<>();
        Long regimentId = regimentRequest.getId();
        Optional<Regiment> regimentOtp = regimentRepository.findById(regimentId);
        if (regimentOtp.isEmpty()) {
            throw new BadRequestException(message.objectNotFoundByIdMessage("Regiment", regimentId));
        }
        for (RegimentDetailRequest regimentDetailRequest : regimentRequest.getRegimentDetailRequests()) {
            if (medicineRequests.containsKey(regimentDetailRequest.getMedicineId())) {
                throw new BadRequestException("Duplicate medicine " + regimentDetailRequest.getMedicineId());
            }
            regimentDetailRequetsMap.put(regimentDetailRequest.getMedicineId(), regimentDetailRequest);
            medicineRequests.put(regimentDetailRequest.getMedicineId(), regimentDetailRequest.getQuantity());
        }
        List<Medicine> medicines = medicineRepository.findByIdIn(medicineRequests.keySet());
        if (medicines.size() < medicineRequests.size()) {
            throw new BadRequestException("Some demicine not exist.");
        }
        List<RegimentDetail> regimentDetails = new ArrayList<>();
        for (Medicine medicine : medicines) {
            Long medicineId = medicine.getId();
            int medicineQuantity = medicineRequests.get(medicineId);
            RegimentDetailRequest regimentDetailRequest = regimentDetailRequetsMap.get(medicineId);
            RegimentDetail regimentDetail = regimentDetailMapper.mapDtoToEntity(regimentDetailRequest);
            regimentDetail.setQuantity(medicineQuantity);
            regimentDetail.setMedicine(medicine);
            regimentDetail.setRegiment(regimentOtp.get());
            regimentDetails.add(regimentDetail);
        }
        regimentDetailRepository.saveAll(regimentDetails);
    }

    @Override
    public Map<Long, RegimentNotifiactionResponse> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime startTime, LocalTime endTime) {

        List<RegimentDetail> regimentDetails = regimentDetailRepository.findByStatusAndTime(
                regimentStatus,
                startTime, endTime);

        if (regimentDetails.isEmpty()) {
            throw new BadRequestException(message.emptyList("Regiment"));
        }
        Map<Long, RegimentNotifiactionResponse> result = new HashMap<>();
        for (RegimentDetail regimentDetail : regimentDetails) {
            Regiment regiment = regimentDetail.getRegiment();

            RegimentNotifiactionResponse notificationResponse = result.get(regiment.getId());
            if (notificationResponse == null) {
                notificationResponse = RegimentNotifiactionResponse.builder()
                        .regimentName(regiment.getName())
                        .regimentImage(regiment.getImage())
                        .doseRegiment(regiment.getDoseRegiment())
                        .userDeviceToken(regiment.getDeviceToken())
                        .regimentId(regiment.getId())
                        .takenTime(determineTakenTime.determineTakenTime(regimentDetail).toString())
                        .build();
                result.put(regiment.getId(), notificationResponse);
            }
            result.put(regiment.getId(), notificationResponse);
        }

        return result;
    }

}
