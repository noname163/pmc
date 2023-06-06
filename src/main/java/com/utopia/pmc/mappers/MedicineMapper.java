package com.utopia.pmc.mappers;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.medicine.MedicineRequest;
import com.utopia.pmc.data.entities.medicine.Medicine;

@Component
public class MedicineMapper {
    public Medicine mapDtoToEntity(MedicineRequest medicineRequest) {
        return Medicine
                .builder()
                .name(medicineRequest.getName())
                .describe(medicineRequest.getDescription())
                .expiredTime(medicineRequest.getExpiredTime())
                .period(medicineRequest.getPeriod())
                .note(medicineRequest.getNote())
                .image(medicineRequest.getImage())
                .consumerWay(medicineRequest.getConsumerWay())
                .build();
    }
}
