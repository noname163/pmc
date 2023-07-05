package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotificationResponse;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;
import com.utopia.pmc.data.entities.Regimen;

@Component
public class RegimenMapper {
    public Regimen mapDtoToEntity(RegimenRequest regimentRequest) {
        return Regimen
                .builder()
                .name(regimentRequest.getName())
                .image(regimentRequest.getImage())
                .doseRegiment(regimentRequest.getDoseRegiment())
                .startDate(regimentRequest.getStartDate())
                .deviceToken(regimentRequest.getDeviceToken() != null ? regimentRequest.getDeviceToken() : "empty")
                .period(regimentRequest.getPeriod())
                .build();
    }

    public RegimenNotificationResponse mapEntityToNotificationResponse(Regimen regimen) {
        return RegimenNotificationResponse.builder()
                .regimentId(regimen.getId())
                .regimentImage(regimen.getImage())
                .regimentName(regimen.getName())
                .userDeviceToken(regimen.getDeviceToken())
                .build();
    }

    public RegimenResponse mapEntityToDtoRegimenResponse(Regimen regiment) {
        return RegimenResponse
                .builder()
                .regimenId(regiment.getId())
                .regimenName(regiment.getName())
                .dosageRegimen(regiment.getDoseRegiment())
                .period(regiment.getPeriod())
                .image(regiment.getImage())
                .status(regiment.getStatus())
                .startDate(regiment.getStartDate() == null ? null : regiment.getStartDate())
                .takenTime(regiment.getTakenTime())
                .missedTime(regiment.getMissedTime())
                .isAlert(regiment.getIsAlert())
                .totalTypeMedicine(regiment.getRegimentDetails().size())
                .build();
    }

    public List<RegimenResponse> mapEntitiesToDtoRegimenResponse(List<Regimen> regimens) {
        return regimens
                .stream()
                .map(this::mapEntityToDtoRegimenResponse)
                .collect(Collectors.toList());
    }

    public List<Regimen> mapDtosToEntities(List<RegimenRequest> regimentRequests) {
        return regimentRequests
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
