package com.utopia.pmc.services.regimentDetail;

import java.time.LocalTime;
import java.util.Map;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.request.regiment.RegimentRequest;
import com.utopia.pmc.data.dto.response.regiment.RegimentNotifiactionResponse;

public interface RegimentDetailService {
    public void createRegimentDetails(RegimentRequest regimentRequest);

    public Map<Long, RegimentNotifiactionResponse> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime starTime,
            LocalTime endTime);
}
