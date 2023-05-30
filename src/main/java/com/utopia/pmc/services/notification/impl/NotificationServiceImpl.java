package com.utopia.pmc.services.notification.impl;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.response.notification.NotificationResponse;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotifiactionResponse;
import com.utopia.pmc.services.expoSendNotification.SendNotificationService;
import com.utopia.pmc.services.notification.NotificationService;
import com.utopia.pmc.services.regimenDetail.RegimenDetailService;

import io.github.jav.exposerversdk.PushClientException;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SendNotificationService sendNotificationService;
    @Autowired
    private RegimenDetailService regimentDetailService;

    @Override
    @Scheduled(fixedDelay = 1000)
    public void remindMedicineScheduler() {

        RegimentStatus regimentStatus = RegimentStatus.INPROCESS;
        LocalTime starTime = LocalTime.now();
        LocalTime endTime = starTime.plusSeconds(1);

        Map<Long, RegimenNotifiactionResponse> regimentResponse = regimentDetailService
                .getRegimentDetailResponsesByStatusAndTime(regimentStatus,
                        starTime, endTime);

        Map<String, NotificationResponse> notificationResponses = new HashMap<>();

        for (RegimenNotifiactionResponse regimentDetailResponse : regimentResponse.values()) {
            NotificationResponse notificationResponse = NotificationResponse
                    .builder()
                    .data(regimentDetailResponse)
                    .title("You Have An Dose Regiment At " + regimentDetailResponse.getTakenTime())
                    .message("Regiment Name " + regimentDetailResponse.getRegimentName() + "\n"
                            + "Taken in " + regimentDetailResponse.getDoseRegiment() + " " +
                            regimentDetailResponse.getPeriod())
                    .build();
            notificationResponses.put(regimentDetailResponse.getUserDeviceToken(), notificationResponse);
        }
        try {
            sendNotificationService.sendNotifications(notificationResponses);
        } catch (PushClientException e) {
            e.getMessage();
        }

    }

}
