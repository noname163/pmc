package com.utopia.pmc.services.expoSendNotification;

import io.github.jav.exposerversdk.PushClientException;

public interface SendNotificationService {
    public void sendNotification(String recipient, String title, String message, String data);

    public void sendRegimenNotifications() throws PushClientException;
}
