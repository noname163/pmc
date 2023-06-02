package com.utopia.pmc.mappers;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.history.HistoryRequest;
import com.utopia.pmc.data.entities.History;

@Component
public class HistoryMapper {
    public History mapDtoToEntity(HistoryRequest historyRequest) {
        return History
                .builder()
                .dateTaken(historyRequest.getTakenDate())
                .takenStatus(historyRequest.getTakenStatus())
                .timeTaken(historyRequest.getTakenTime())
                .build();
    }
}
