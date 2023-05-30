package com.utopia.pmc.data.dto.request.regimen;

import java.util.List;

import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.dto.request.regimendetail.RegimenDetailRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimenRequest {
    private Long id;
    private String name;
    private String image;
    private Period period;
    private Integer doseRegiment;
    private Boolean startNow;
    private String deviceToken;
    private List<RegimenDetailRequest> regimentDetailRequests;
}
