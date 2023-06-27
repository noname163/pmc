package com.utopia.pmc.data.dto.response.history;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HistoryDetailRespone {
    private Long regimenDetailId;
    private String regimenImage;
    private String medicineName;
    private Integer takenQuantity;
}
