package com.utopia.pmc.mappers;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.history.HistoryRequest;
import com.utopia.pmc.data.entities.History;

@Component
public class HistoryMapper {
    public History mapDtoToEntity(HistoryRequest historyRequest) {
        return History
                .builder()
                .takenStatus(historyRequest.getTakenStatus())
                .numberOfTaken(historyRequest.getMedicineIds().size())
                .build();
    }
}
