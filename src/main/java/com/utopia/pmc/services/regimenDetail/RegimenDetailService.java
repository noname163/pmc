package com.utopia.pmc.services.regimenDetail;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;

public interface RegimenDetailService {
    public void createRegimentDetails(RegimenRequest regimentRequest);

    public Map<String, List<RegimenDetailResponse>> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime starTime,
            LocalTime endTime);
}
