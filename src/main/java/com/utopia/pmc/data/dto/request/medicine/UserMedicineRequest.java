package com.utopia.pmc.data.dto.request.medicine;

import com.utopia.pmc.data.constants.others.ConsumerWay;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserMedicineRequest {
    private String name;
    private String image;
    private String medicineForm;
    private ConsumerWay consumerWay;
}
