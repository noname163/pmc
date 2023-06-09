package com.utopia.pmc.data.dto.response.regimen;


import java.time.LocalDate;

import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.constants.statuses.RegimentStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimenResponse {
    private Long regimenId;
    private String regimenName;
    private String image;
    private Integer dosageRegimen;
    private Period period;
    private LocalDate startDate;
    private RegimentStatus status;
    private Integer takenTime;
    private Integer missedTime;
    private Integer totalTypeMedicine;
    private Boolean isAlert;
}
