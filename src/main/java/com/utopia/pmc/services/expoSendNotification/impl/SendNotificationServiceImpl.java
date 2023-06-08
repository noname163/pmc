package com.utopia.pmc.services.expoSendNotification.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.others.Validation;
import com.utopia.pmc.data.constants.statuses.NotificationStatus;
import com.utopia.pmc.data.database.DailyData;
import com.utopia.pmc.data.dto.response.notification.NotificationResponse;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotificationResponse;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.mappers.NotificationMapper;
import com.utopia.pmc.mappers.RegimenDetailMapper;
import com.utopia.pmc.services.expoSendNotification.LogNotificationStatus;
import com.utopia.pmc.services.expoSendNotification.SendNotificationService;
import com.utopia.pmc.utils.RegimenFunction;

import io.github.jav.exposerversdk.ExpoPushMessage;
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
    @Autowired
    private RegimenFunction regimentFunction;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Validation.TIME_FORMAT_WITH_SECOND);

    @Override
    public void sendNotification(String recipient, String title, String message, String data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendNotification'");
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void sendRegimenNotifications() throws PushClientException {
        Map<String, List<RegimenDetailResponse>> data = DailyData.getData();
        LocalTime currentTime = LocalTime.now();

        if (data == null || data.isEmpty()) {
            log.info("Notthing to send at " + currentTime);
            throw new BadRequestException("Notthing to send");
        }

        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        Map<String, NotificationResponse> notificationData = handlerData(data);

        if (!notificationData.isEmpty()) {
            for (String key : notificationData.keySet()) {
                Map<String, Object> dataSend = new HashMap<>();
                String deviceToken = key;
                NotificationResponse notificationResponse = notificationData.get(key);

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
            try {
                logForSendNotification.logForSendNotification(client, expoPushMessages, messageRepliesFutures, currentTime.format(formatter));
            } catch (InterruptedException e) {
                System.out.println("Errors " + e.getMessage());
            } catch (ExecutionException e) {
                System.out.println("Errors " + e.getMessage());
            }
        } else {
            log.info("Notthing to send at " + currentTime);
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

    private Map<String, NotificationResponse> handlerData(Map<String, List<RegimenDetailResponse>> dataset) {
        
        
        LocalTime currentTime = LocalTime.now();
        Map<String, NotificationResponse> result = new HashMap<>();
        List<RegimenDetailResponse> regimenDetailResponses = dataset.get(currentTime.format(formatter));

        if (regimenDetailResponses != null) {
            for (RegimenDetailResponse regimenDetailResponse : regimenDetailResponses) {

                if (result.get(regimenDetailResponse.getDeviceToken()) == null) {
                    RegimenNotificationResponse regimenNotificationResponse = RegimenNotificationResponse
                            .builder()
                            .regimentId(regimenDetailResponse.getRegimenId())
                            .regimentName(regimenDetailResponse.getRegimenName())
                            .userDeviceToken(regimenDetailResponse.getDeviceToken())
                            .takenTime(currentTime.toString())
                            .build();
                    NotificationResponse notificationResponse = notificationMapper
                            .mapRegimentDetailToNotification(regimenNotificationResponse);
                    result.put(regimenDetailResponse.getDeviceToken(), notificationResponse);
                }

            }
        }
        return result;
    }
}
