package com.utopia.pmc.services.expoSendNotification;

import java.util.List;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushMessageTicketPair;
import io.github.jav.exposerversdk.PushClient;

public interface LogNotificationStatus {
    public void logForSendNotification(PushClient client, List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets);
}
