package com.utopia.pmc.data.dto.request.history;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utopia.pmc.data.constants.statuses.TakenStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HistoryRequest {
    @NotNull(message = "Regimen id is required")
    private Long regimentId;

    @NotNull(message = "Taken status is required")
    private TakenStatus takenStatus;

    @NotNull(message = "Medicine is required")
    private Set<Long> medicineIds;
}
