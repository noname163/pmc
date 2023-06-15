package com.utopia.pmc.data.dto.request.regimen;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.utopia.pmc.data.constants.others.Period;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EditRegimenRequest {
    @NotNull(message = "Regimen id is required")
    private Long regimenId;
    @NotBlank(message = "Regimen name is required")
    private String regimenName;
    @NotBlank(message = "Regimen image is required")
    private String image;
    @NotNull(message = "Dose regimen is required")
    private Integer dosageRegimen;
    @NotNull(message = "Peroid is required")
    private Period period;
    @NotNull(message = "Peroid is required")
    private LocalDate startDate;
}
