package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.history.HistoryRequest;
import com.utopia.pmc.data.dto.request.history.HistoryRequestWithMedicineName;
import com.utopia.pmc.data.dto.response.history.HistoryResponse;
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
    public History mapDtoToEntity(HistoryRequestWithMedicineName historyRequest) {
        return History
                .builder()
                .takenStatus(historyRequest.getTakenStatus())
                .numberOfTaken(historyRequest.getMedicineName().size())
                .build();
    }

    
    public HistoryResponse mapEntityToDto(History history) {
        return HistoryResponse
                .builder()
                .historyId(history.getId())
                .regimenName(history.getRegiment().getName())
                .takenDate(history.getDateTaken())
                .timeTaken(history.getTimeTaken())
                .takenStatus(history.getTakenStatus())
                .totalMedicine(history.getTotalMedicine())
                .numberOfTaken(history.getNumberOfTaken())
                .build();
    }

    public List<HistoryResponse> mapEntityToDtos(List<History> histories) {
        return histories.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }
}
