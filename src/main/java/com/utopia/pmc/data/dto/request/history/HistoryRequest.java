package com.utopia.pmc.data.dto.request.history;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.utopia.pmc.data.constants.statuses.TakenStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HistoryRequest {
    private Long regimentId;
    private Long userId;
    private TakenStatus takenStatus;
    private List<Integer> medicineIds;
    private LocalTime takenTime;
    private LocalDate takenDate;
}
