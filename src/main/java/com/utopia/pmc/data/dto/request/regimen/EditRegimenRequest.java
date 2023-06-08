package com.utopia.pmc.data.dto.request.regimen;

import java.time.LocalDate;

import com.utopia.pmc.data.constants.others.Period;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EditRegimenRequest {
    private Long regimenId;
    private String regimenName;
    private String image;
    private Integer dosageRegimen;
    private Period period;
    private LocalDate startDate;
}
