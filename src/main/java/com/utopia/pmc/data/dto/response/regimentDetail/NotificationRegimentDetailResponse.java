package com.utopia.pmc.data.dto.response.regimentDetail;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NotificationRegimentDetailResponse {
    private Integer doseRegiment;
    private String takenTime;
    private String regimentName;
    private String period; 
    private String regimentImage;
    private String userDeviceToken;
    private List<RegimentDetailResponse> regimentDetailResponses;
}
