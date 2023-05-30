package com.utopia.pmc.services.regimenDetail;

import java.time.LocalTime;
import java.util.Map;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotificationResponse;

public interface RegimenDetailService {
    public void createRegimentDetails(RegimenRequest regimentRequest);

    public Map<Long, RegimenNotificationResponse> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime starTime,
            LocalTime endTime);
}
