package com.utopia.pmc.services.expoSendNotification;

import java.util.Map;

import com.utopia.pmc.data.dto.response.regimentDetail.NotificationRegimentDetailResponse;

import io.github.jav.exposerversdk.PushClientException;

public interface SendNotificationService {
    public void sendNotification(String recipient, String title, String message, String data);

    public void sendNotifications(
            Map<Long, NotificationRegimentDetailResponse> data) throws PushClientException;
}
