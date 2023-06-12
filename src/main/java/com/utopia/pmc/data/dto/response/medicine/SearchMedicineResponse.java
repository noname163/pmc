package com.utopia.pmc.data.dto.response.medicine;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SearchMedicineResponse {
    private Long id;
    private String name;
    private String medicineForm;
    private String consumerWay;

}
