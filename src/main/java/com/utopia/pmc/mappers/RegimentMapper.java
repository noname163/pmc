package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.regiment.RegimentRequest;
import com.utopia.pmc.data.entities.Regiment;

@Component
public class RegimentMapper {
    public Regiment mapDtoToEntity(RegimentRequest regimentRequest) {
        return Regiment
                .builder()
                .name(regimentRequest.getName())
                .image(regimentRequest.getImage())
                .doseRegiment(regimentRequest.getDoseRegiment())
                .deviceToken(regimentRequest.getDeviceToken())
                .period(regimentRequest.getPeriod())
                .build();
    }

    public List<Regiment> mapDtosToEntities(List<RegimentRequest> regimentRequests) {
        return regimentRequests
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
