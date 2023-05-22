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
import com.utopia.pmc.data.dto.request.RegimentDetailRequest;
import com.utopia.pmc.data.dto.request.RegimentRequest;
import com.utopia.pmc.data.dto.response.regimentDetail.RegimentDetailResponse;
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
    public List<RegimentDetailResponse> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime starTime, LocalTime endTime) {
        List<RegimentDetail> regimentDetailResponses = regimentDetailRepository.findByStatusAndTime(
                regimentStatus,
                starTime, endTime);
        if (regimentDetailResponses.isEmpty()) {
            throw new EmptyException(message.emptyList("Regiment"));
        }
        return regimentDetailMapper.mapEntityToDtos(regimentDetailResponses);
    }

}
