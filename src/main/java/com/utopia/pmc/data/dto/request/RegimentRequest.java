package com.utopia.pmc.data.dto.request;

import com.utopia.pmc.data.constants.others.Period;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimentRequest {
    private String name;
    private Period period;
    private Integer doseRegiment;
}
