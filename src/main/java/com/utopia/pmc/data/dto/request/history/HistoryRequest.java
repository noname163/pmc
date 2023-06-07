package com.utopia.pmc.data.dto.request.history;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utopia.pmc.data.constants.statuses.TakenStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HistoryRequest {
    private Long regimentId;
    private TakenStatus takenStatus;
    private Set<Long> medicineIds;
}
