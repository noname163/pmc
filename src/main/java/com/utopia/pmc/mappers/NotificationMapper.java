package com.utopia.pmc.mappers;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.response.notification.NotificationResponse;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotificationResponse;

@Component
public class NotificationMapper {
    public NotificationResponse mapRegimentDetailToNotification(
            RegimenNotificationResponse regimenNotificationResponse) {
        return NotificationResponse
                .builder()
                .data(regimenNotificationResponse)
                .title("You Have An Dose Regiment At " + regimenNotificationResponse.getTakenTime())
                .message("Regiment Name " + regimenNotificationResponse.getRegimentName() + "\n"
                        + "Taken in " + regimenNotificationResponse.getDoseRegiment() + " " +
                        regimenNotificationResponse.getPeriod())
                .build();
    }
}
