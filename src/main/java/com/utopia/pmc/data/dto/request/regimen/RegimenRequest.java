package com.utopia.pmc.data.dto.request.regimen;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.dto.request.regimendetail.RegimenDetailRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimenRequest {
    @JsonIgnore
    private Long id;

    @NotBlank(message = "Regimen name is required")
    private String name;

    @NotBlank(message = "Regimen image is required")
    private String image;

    @NotNull(message = "Period is required")
    private Period period;

    @NotNull(message = "Dose regimen id is required")
    private Integer doseRegiment;

    @NotNull(message = "Start status is required")
    private Boolean startNow;

    private LocalDate startDate;

    @NotBlank(message = "Device token is required")
    private String deviceToken;

    @NotNull(message = "Regimen detail is required")
    private List<RegimenDetailRequest> regimentDetailRequests;
}
