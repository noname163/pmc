package com.utopia.pmc.services.expoSendNotification;

import java.util.Map;

import com.utopia.pmc.data.dto.response.notification.NotificationResponse;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotifiactionResponse;

import io.github.jav.exposerversdk.PushClientException;

public interface SendNotificationService {
    public void sendNotification(String recipient, String title, String message, String data);

    public void sendNotifications(
            Map<String, NotificationResponse> data) throws PushClientException;
}
