package com.utopia.pmc.data.dto.response.history;

import java.time.LocalDate;
import java.time.LocalTime;

import com.utopia.pmc.data.constants.statuses.TakenStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HistoryResponse {
    private Long historyId;
    private String regimenName;
    private LocalDate takenDate;
    private LocalTime timeTaken;
    private TakenStatus takenStatus;
    private Integer totalMedicine;
    private Integer numberOfTaken;
}
