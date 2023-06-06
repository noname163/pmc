package com.utopia.pmc.data.dto.response.history;

import java.time.LocalDate;

import com.utopia.pmc.data.constants.statuses.TakenStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HistoryResponse {
    private String regimenName;
    private LocalDate takenDate;
    private TakenStatus takenStatus;
    private Integer totalMedicine;
    private Integer numberOfTaken;
}
