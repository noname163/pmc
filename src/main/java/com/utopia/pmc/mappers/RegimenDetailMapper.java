package com.utopia.pmc.mappers;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utopia.pmc.data.constants.others.Validation;
import com.utopia.pmc.data.dto.request.regimendetail.RegimenDetailRequest;
import com.utopia.pmc.data.entities.RegimentDetail;
import com.utopia.pmc.utils.ConvertStringToLocalTime;

@Component
public class RegimenDetailMapper {
    @Autowired
    private ConvertStringToLocalTime convertStringToLocalTime;
    public RegimentDetail mapDtoToEntity(RegimenDetailRequest regimentDetailRequest) {
        LocalTime firstTime = convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT,
                regimentDetailRequest.getFirstTime());
        LocalTime secondTime = convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT,
                regimentDetailRequest.getSecondTime());
        LocalTime thirdTime = convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT,
                regimentDetailRequest.getThirdTime());
        LocalTime fourthTime = convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT,
                regimentDetailRequest.getFourthTime());
        return RegimentDetail
                .builder()
                .takenQuantity(regimentDetailRequest.getTakenQuantity())
                .firstTime(firstTime)
                .secondTime(secondTime)
                .thirdTime(thirdTime)
                .fourthTime(fourthTime)
                .dose(regimentDetailRequest.getDose())
                .build();
    }

    public List<RegimentDetail> mapDtosToEntities(List<RegimenDetailRequest> regimentDetailRequests) {
        return regimentDetailRequests
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

}
