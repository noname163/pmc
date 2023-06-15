package com.utopia.pmc.data.dto.request.history;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.utopia.pmc.data.constants.statuses.TakenStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HistoryRequestWithMedicineName {
    @NotNull(message = "Regimen id is required")
    private Long regimentId;

    @NotNull(message = "Taken status is required")
    private TakenStatus takenStatus;

    @NotNull(message = "Medicine is required")
    private Set<String> medicineName;
}
