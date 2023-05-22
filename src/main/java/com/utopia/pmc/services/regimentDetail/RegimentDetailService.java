package com.utopia.pmc.services.regimentDetail;

import java.time.LocalTime;
import java.util.List;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.request.RegimentRequest;
import com.utopia.pmc.data.dto.response.regimentDetail.RegimentDetailResponse;

public interface RegimentDetailService {
    public void createRegimentDetails(RegimentRequest regimentRequest);

    public List<RegimentDetailResponse> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime starTime,
            LocalTime endTime);
}
