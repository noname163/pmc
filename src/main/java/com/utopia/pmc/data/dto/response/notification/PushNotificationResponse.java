package com.utopia.pmc.data.dto.response.notification;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PushNotificationResponse {
    private int status;
    private String message;
}
