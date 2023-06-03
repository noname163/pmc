package com.utopia.pmc.data.dto.request.regimen;

import java.time.LocalDate;
import java.util.List;

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
    private String name;
    private String image;
    private Period period;
    private Integer doseRegiment;
    private Boolean startNow;
    private LocalDate startDate;
    private String deviceToken;
    private List<RegimenDetailRequest> regimentDetailRequests;
}
