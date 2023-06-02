package com.utopia.pmc.data.dto.response.regimen;

import java.time.LocalTime;
import java.util.List;

import com.utopia.pmc.data.constants.statuses.NotificationStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimenNotificationResponse {
    private String regimentName;
    private String regimentImage;
    private Long regimentId;
    private String takenTime;
    private String userDeviceToken;
    private NotificationStatus notificationStatus;
}
