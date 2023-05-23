package com.utopia.pmc.services.notification.impl;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.response.regimentDetail.NotificationRegimentDetailResponse;
import com.utopia.pmc.services.expoSendNotification.SendNotificationService;
import com.utopia.pmc.services.notification.NotificationService;
import com.utopia.pmc.services.regimentDetail.RegimentDetailService;

import io.github.jav.exposerversdk.PushClientException;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SendNotificationService sendNotificationService;
    @Autowired
    private RegimentDetailService regimentDetailService;

    @Override
    @Scheduled(fixedDelay = 10000)
    public void remindMedicineScheduler() {
        System.out.println("Auto get data");

        RegimentStatus regimentStatus = RegimentStatus.INPROCESS;
        LocalTime starTime = LocalTime.now();
        LocalTime endTime = starTime.plusMinutes(10);

        Map<Long, NotificationRegimentDetailResponse> regimentResponse = regimentDetailService
                .getRegimentDetailResponsesByStatusAndTime(regimentStatus,
                        starTime, endTime);

        String title = "Take medicine notification";
        String message = "";
        try {
            System.out.println("Sending notification");
            sendNotificationService.sendNotifications(title, message, regimentResponse);
        } catch (PushClientException e) {
            e.printStackTrace();
        }

    }

}
