package com.utopia.pmc.data.dto.response.regimen;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimenResponse {
    private String dose;
    private String takenTime;
    private Long regimentName;
}
