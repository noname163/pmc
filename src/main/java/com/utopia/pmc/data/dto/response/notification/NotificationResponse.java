package com.utopia.pmc.data.dto.response.notification;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NotificationResponse {
    private String title;
    private String message;
    private Object data;
}
