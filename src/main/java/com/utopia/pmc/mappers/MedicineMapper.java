package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.medicine.MedicineRequest;
import com.utopia.pmc.data.dto.request.medicine.UserMedicineRequest;
import com.utopia.pmc.data.dto.response.medicine.SearchMedicineResponse;
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

    public SearchMedicineResponse mapEntityToDto(Medicine medicine) {
        return SearchMedicineResponse
                .builder()
                .id(medicine.getId())
                .imageUrl(medicine.getImage())
                .name(medicine.getName())
                .medicineForm(medicine.getDosageForm().getForm())
                .consumerWay(medicine.getConsumerWay().toString())
                .build();
    }

    public Medicine mapDtoToEntity(UserMedicineRequest userMedicineRequest) {
        return Medicine
                .builder()
                .name(userMedicineRequest.getName())
                .image(userMedicineRequest.getImage())
                .consumerWay(userMedicineRequest.getConsumerWay())
                .build();
    }

    public List<SearchMedicineResponse> mapEntitiesToDtos(List<Medicine> medicines) {
        return medicines.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }
}
