package com.utopia.pmc.services.history;

import java.util.List;

import com.utopia.pmc.data.dto.request.history.HistoryRequest;
import com.utopia.pmc.data.dto.request.history.HistoryRequestWithMedicineName;
import com.utopia.pmc.data.dto.response.history.HistoryResponse;

public interface HistoryService {
    public void createHistory(HistoryRequest historyRequest);

    public void createHistoryWithMedicineName(HistoryRequestWithMedicineName historyRequest);

    public List<HistoryResponse> getHistoryByUser();
}
