package com.utopia.pmc.services.expoSendNotification;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushMessageTicketPair;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;

public interface LogNotificationStatus {
    public void logForSendNotification(PushClient client, List<ExpoPushMessage> expoPushMessages,
            List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures, String takenTime)
            throws InterruptedException, ExecutionException;
}
