package com.utopia.pmc.data.dto.response;

import org.springframework.stereotype.Component;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Component
public class PushNotificationResponse {
    private int status;
    private String message;
}
