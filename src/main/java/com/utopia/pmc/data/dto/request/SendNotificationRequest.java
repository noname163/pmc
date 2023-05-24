package com.utopia.pmc.data.dto.request;

import org.springframework.stereotype.Component;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Component
public class SendNotificationRequest {
    private String title;
    private String message;
    private String topic;
    private String token;
}
