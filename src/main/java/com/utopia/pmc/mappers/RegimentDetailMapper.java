package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.RegimentDetailRequest;
import com.utopia.pmc.data.entities.RegimentDetail;

@Component
public class RegimentDetailMapper {
    public RegimentDetail mapDtoToEntity(RegimentDetailRequest regimentDetailRequest) {
        return RegimentDetail
                .builder()
                .quantity(regimentDetailRequest.getQuantity())
                .firstTime(regimentDetailRequest.getFirstTime())
                .secondTime(regimentDetailRequest.getSecondTime())
                .thirdTime(regimentDetailRequest.getThirdTime())
                .fourthTime(regimentDetailRequest.getFourthTime())
                .dose(regimentDetailRequest.getDose())
                .build();
    }

    public List<RegimentDetail> mapDtosToEntities(List<RegimentDetailRequest> regimentDetailRequests) {
        return regimentDetailRequests
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
