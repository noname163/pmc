package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.response.history.HistoryDetailRespone;
import com.utopia.pmc.data.entities.HistoryDetail;

@Component
public class HistoryDetailMapper {
    public HistoryDetailRespone mapEntityToDto(HistoryDetail historyDetail) {
        return HistoryDetailRespone
                .builder()
                .medicineName(historyDetail.getRegimenDetail().getMedicineName())
                .takenQuantity(historyDetail.getRegimenDetail().getTakenQuantity())
                .regimenDetailId(historyDetail.getRegimenDetail().getId())
                .build();
    }

    public List<HistoryDetailRespone> mapEntityToDtos(List<HistoryDetail> historyDetails) {
        return historyDetails.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }
}
