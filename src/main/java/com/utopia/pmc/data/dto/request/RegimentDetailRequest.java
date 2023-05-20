package com.utopia.pmc.data.dto.request;

import java.time.LocalTime;

import com.utopia.pmc.data.constants.others.Dose;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegimentDetailRequest {
    private Integer quantity;
    private Dose dose;
    private LocalTime firstTime;
    private LocalTime secondTime;
    private LocalTime thirdTime;
    private LocalTime fourthTime;
    private Long regimentId;
    private Long medicineId;
}
