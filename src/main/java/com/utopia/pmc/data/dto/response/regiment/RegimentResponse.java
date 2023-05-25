package com.utopia.pmc.data.dto.response.regiment;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimentResponse {
    private String dose;
    private String takenTime;
    private Long regimentName;
}
