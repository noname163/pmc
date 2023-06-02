package com.utopia.pmc.services.notification.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.database.DailyData;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.services.notification.NotificationService;
import com.utopia.pmc.services.regimenDetail.RegimenDetailService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RegimenDetailService regimentDetailService;

    @Override
    @Scheduled(fixedDelay = 14400000, initialDelay = 1000)
    public void remindMedicineScheduler() {

        RegimentStatus regimentStatus = RegimentStatus.INPROCESS;
        LocalTime starTime = LocalTime.now();
        LocalTime endTime = starTime.plusHours(4);

        Map<String, List<RegimenDetailResponse>> regimentResponse = regimentDetailService
                .getRegimentDetailResponsesByStatusAndTime(regimentStatus,
                        starTime, endTime);

        for (String key : regimentResponse.keySet()) {
            DailyData.addDataset(key, regimentResponse.get(key));
        }
    }

}
