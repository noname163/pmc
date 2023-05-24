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
    @Scheduled(fixedDelay = 1000)
    public void remindMedicineScheduler() {

        RegimentStatus regimentStatus = RegimentStatus.INPROCESS;
        LocalTime starTime = LocalTime.now();
        LocalTime endTime = starTime.plusSeconds(1);

        Map<Long, NotificationRegimentDetailResponse> regimentResponse = regimentDetailService
                .getRegimentDetailResponsesByStatusAndTime(regimentStatus,
                        starTime, endTime);

        try {
            sendNotificationService.sendNotifications( regimentResponse);
        } catch (PushClientException e) {
            e.getMessage();
        }

    }

}
