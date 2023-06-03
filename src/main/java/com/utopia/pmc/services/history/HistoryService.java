package com.utopia.pmc.services.history;

import com.utopia.pmc.data.dto.request.history.HistoryRequest;

public interface HistoryService {
    public void createHistory(HistoryRequest historyRequest);
}
