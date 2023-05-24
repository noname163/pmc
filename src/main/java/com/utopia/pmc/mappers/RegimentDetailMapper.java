package com.utopia.pmc.mappers;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utopia.pmc.data.constants.others.Validation;
import com.utopia.pmc.data.dto.request.RegimentDetailRequest;
import com.utopia.pmc.data.dto.response.regimentDetail.RegimentDetailResponse;
import com.utopia.pmc.data.entities.Medicine;
import com.utopia.pmc.data.entities.RegimentDetail;
import com.utopia.pmc.utils.ConvertStringToLocalTime;
import com.utopia.pmc.utils.DetermineTakenTime;

@Component
public class RegimentDetailMapper {
    @Autowired
    private ConvertStringToLocalTime convertStringToLocalTime;
    @Autowired
    private DetermineTakenTime determineTakenTime;
    public RegimentDetail mapDtoToEntity(RegimentDetailRequest regimentDetailRequest) {
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
                .quantity(regimentDetailRequest.getQuantity())
                .firstTime(firstTime)
                .secondTime(secondTime)
                .thirdTime(thirdTime)
                .fourthTime(fourthTime)
                .dose(regimentDetailRequest.getDose())
                .build();
    }

    public List<RegimentDetail> mapDtosToEntities(List<RegimentDetailRequest> regimentDetailRequests) {
        return regimentDetailRequests
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    public RegimentDetailResponse mapEntityToDto(RegimentDetail regimentDetail) {
        Medicine medicine = regimentDetail.getMedicine();
        LocalTime takenTime = determineTakenTime.determineTakenTime(regimentDetail);

        return RegimentDetailResponse.builder()
                .dose(regimentDetail.getDose().toString())
                .regimentId(regimentDetail.getRegiment().getId())
                .takenTime(takenTime)
                .quantity(regimentDetail.getQuantity())
                .medicineImage(medicine.getImage())
                .medicinename(medicine.getName())
                .build();
    }

    

    public List<RegimentDetailResponse> mapEntityToDtos(List<RegimentDetail> regimentDetails) {
        return regimentDetails.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }
}
