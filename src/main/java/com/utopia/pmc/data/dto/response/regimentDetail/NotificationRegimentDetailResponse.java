package com.utopia.pmc.data.dto.response.regimentDetail;

import java.util.List;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NotificationRegimentDetailResponse {
    private Integer doseRegiment;
    private String takenTime;
    private Long regimentName;
    private String period; 
    private String regimentImage;
    private List<RegimentDetailResponse> regimentDetailResponses;
}
