package com.utopia.pmc.data.dto.request.medicine;

import java.util.List;

import com.utopia.pmc.data.constants.others.ConsumerWay;
import com.utopia.pmc.data.constants.others.Period;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MedicineRequest {
    private String image;
    private String name;
    private String description;
    private Integer expiredTime;
    private Period period;
    private String note;
    private ConsumerWay consumerWay;
    private List<Long> useOfMedicineIds;
    private Long dosageFormId;
    private Long classificationId;
}
