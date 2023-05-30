package com.utopia.pmc.services.expoSendNotification.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.database.DailyData;
import com.utopia.pmc.data.dto.response.notification.NotificationResponse;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotificationResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.mappers.NotificationMapper;
import com.utopia.pmc.services.expoSendNotification.LogNotificationStatus;
import com.utopia.pmc.services.expoSendNotification.SendNotificationService;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushMessageTicketPair;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SendNotificationServiceImpl implements SendNotificationService {

    @Autowired
    private LogNotificationStatus logForSendNotification;
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public void sendNotification(String recipient, String title, String message, String data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendNotification'");
    }

    @Override
    @Scheduled(fixedDelay = 1000)
    public void sendRegimenNotifications() throws PushClientException {
        Map<String, List<Object>> data = DailyData.getData();
        LocalTime currentTime = LocalTime.now();
        
        if (data == null || data.isEmpty()) {
            log.info("Notthing to send at " + currentTime);
            throw new BadRequestException("Notthing to send");
        }
        
        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        List<Object> dataset = data.get(currentTime.toString());

        for (Object object : dataset) {
            Map<String, Object> dataSend = new HashMap<>();

            RegimenNotificationResponse notificationRegimentResponse = (RegimenNotificationResponse) object;
            String deviceToken = notificationRegimentResponse.getUserDeviceToken();
            NotificationResponse notificationResponse = notificationMapper
                    .mapRegimentDetailToNotification(notificationRegimentResponse);

            dataSend.put("data", notificationResponse.getData());

            ExpoPushMessage expoPushMessage = setExpoPushMessage(
                    deviceToken,
                    notificationResponse.getTitle(),
                    notificationResponse.getMessage(),
                    dataSend);

            expoPushMessages.add(expoPushMessage);

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
