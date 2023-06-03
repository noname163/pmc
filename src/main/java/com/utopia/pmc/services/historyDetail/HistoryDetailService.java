package com.utopia.pmc.services.historyDetail;

import java.util.Set;

import com.utopia.pmc.data.entities.History;

public interface HistoryDetailService {
    public void createHistoryDetail(History historyId, Set<Long> medicineIds);
}
