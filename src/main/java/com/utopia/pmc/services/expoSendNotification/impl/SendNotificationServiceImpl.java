package com.utopia.pmc.services.expoSendNotification.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.dto.response.regimentDetail.NotificationRegimentDetailResponse;
import com.utopia.pmc.data.dto.response.regimentDetail.RegimentDetailResponse;
import com.utopia.pmc.services.expoSendNotification.LogNotificationStatus;
import com.utopia.pmc.services.expoSendNotification.SendNotificationService;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushMessageTicketPair;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;

@Service
public class SendNotificationServiceImpl implements SendNotificationService {

    @Autowired
    private LogNotificationStatus logForSendNotification;

    @Override
    public void sendNotification(String recipient, String title, String message, String data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendNotification'");
    }

    @Override
    public void sendNotifications(
            Map<Long, NotificationRegimentDetailResponse> data) throws PushClientException {
        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        String title = "";
        String message = "";
        if (!data.isEmpty()) {
            for (NotificationRegimentDetailResponse notificationRegimentDetailResponse : data.values()) {
                Map<String, Object> dataSend = new HashMap<>();
                NotificationRegimentDetailResponse notificationData = notificationRegimentDetailResponse;
                dataSend.put("regiment", notificationData);
                title = "You Have An Dose Regiment At " + notificationData.getTakenTime().toString();
                message = "Regiment Name " + notificationData.getRegimentName() + "\n"
                        + "Taken in " + notificationData.getDoseRegiment() + " " +
                        notificationData.getPeriod();
                ExpoPushMessage expoPushMessage = setExpoPushMessage(
                        notificationRegimentDetailResponse.getUserDeviceToken(),
                        title,
                        message,
                        dataSend);
                expoPushMessages.add(expoPushMessage);
            }
        }

        PushClient client = new PushClient();
        List<List<ExpoPushMessage>> chunks = client.chunkPushNotifications(expoPushMessages);
        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();
        for (List<ExpoPushMessage> chunk : chunks) {
            messageRepliesFutures.add(client.sendPushNotificationsAsync(chunk));
        }

        List<ExpoPushTicket> allTickets = new ArrayList<>();

        for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture : messageRepliesFutures) {
            try {
                for (ExpoPushTicket ticket : messageReplyFuture.get()) {
                    allTickets.add(ticket);
                }
                List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = client
                        .zipMessagesTickets(expoPushMessages, allTickets);
                logForSendNotification.logForSendNotification(client, zippedMessagesTickets);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private ExpoPushMessage setExpoPushMessage(String recipient, String title, String message,
            Map<String, Object> data) {
        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().add(recipient);
        expoPushMessage.setTitle(title);
        expoPushMessage.setBody(message);
        expoPushMessage.setData(data);
        return expoPushMessage;
    }
}
