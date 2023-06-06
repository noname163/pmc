package com.utopia.pmc.mappers;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utopia.pmc.data.constants.others.Validation;
import com.utopia.pmc.data.constants.statuses.NotificationStatus;
import com.utopia.pmc.data.dto.request.regimendetail.RegimenDetailRequest;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.utils.ConvertStringToLocalTime;

@Component
public class RegimenDetailMapper {
        @Autowired
        private ConvertStringToLocalTime convertStringToLocalTime;

        public RegimenDetail mapDtoToEntity(RegimenDetailRequest regimentDetailRequest) {
                LocalTime firstTime = convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT,
                                regimentDetailRequest.getFirstTime());
                LocalTime secondTime = convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT,
                                regimentDetailRequest.getSecondTime());
                LocalTime thirdTime = convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT,
                                regimentDetailRequest.getThirdTime());
                LocalTime fourthTime = convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT,
                                regimentDetailRequest.getFourthTime());
                return RegimenDetail
                                .builder()
                                .takenQuantity(regimentDetailRequest.getTakenQuantity())
                                .firstTime(firstTime)
                                .secondTime(secondTime)
                                .thirdTime(thirdTime)
                                .fourthTime(fourthTime)
                                .build();
        }

        public RegimenDetailResponse mapEntityToDto(RegimenDetail regimenDetail) {
                return RegimenDetailResponse
                                .builder()
                                .regimenId(regimenDetail.getRegimen().getId())
                                .regimenName(regimenDetail.getRegimen().getName())
                                .medicineId(regimenDetail.getMedicine().getId())
                                .numberOfMedicine(regimenDetail.getNumberOfMedicine())
                                .notificationStatus(NotificationStatus.SENDING)
                                .takenQuantity(regimenDetail.getTakenQuantity())
                                .deviceToken(regimenDetail.getRegimen().getDeviceToken())
                                .firstTime(regimenDetail.getFirstTime() == null ? null : regimenDetail.getFirstTime())
                                .secondTime(regimenDetail.getSecondTime() == null ? null
                                                : regimenDetail.getSecondTime())
                                .thirdTime(regimenDetail.getThirdTime() == null ? null : regimenDetail.getThirdTime())
                                .fourthTime(regimenDetail.getFourthTime() == null ? null
                                                : regimenDetail.getFourthTime())
                                .build();
        }

        public List<RegimenDetailResponse> mapEntityToDtos(List<RegimenDetail> regimenDetails) {
                return regimenDetails
                                .stream()
                                .map(this::mapEntityToDto)
                                .collect(Collectors.toList());
        }

        public List<RegimenDetail> mapDtosToEntities(List<RegimenDetailRequest> regimentDetailRequests) {
                return regimentDetailRequests
                                .stream()
                                .map(this::mapDtoToEntity)
                                .collect(Collectors.toList());
        }

}
