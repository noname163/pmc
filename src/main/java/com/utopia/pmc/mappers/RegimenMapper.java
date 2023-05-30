package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;
import com.utopia.pmc.data.entities.Regiment;

@Component
public class RegimenMapper {
    public Regiment mapDtoToEntity(RegimenRequest regimentRequest) {
        return Regiment
                .builder()
                .name(regimentRequest.getName())
                .image(regimentRequest.getImage())
                .doseRegiment(regimentRequest.getDoseRegiment())
                .startDate(regimentRequest.getStartDate())
                .deviceToken(regimentRequest.getDeviceToken())
                .period(regimentRequest.getPeriod())
                .build();
    }

    public RegimenResponse mapEntityToDtoRegimenResponse(Regiment regiment){
        return RegimenResponse
        .builder()
        .regimenName(regiment.getName())
        .dosageRegimen(regiment.getDoseRegiment())
        .period(regiment.getPeriod())
        .image(regiment.getImage())
        .startDate(regiment.getStartDate() == null ? null : regiment.getStartDate())
        .takenTime(regiment.getTakenTime())
        .missedTime(regiment.getMissedTime())
        .totalTypeMedicine(regiment.getRegimentDetails().size())
        .build();
    }

    public List<Regiment> mapDtosToEntities(List<RegimenRequest> regimentRequests) {
        return regimentRequests
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
