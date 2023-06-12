package com.utopia.pmc.data.dto.request.regimendetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utopia.pmc.data.constants.others.Validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EditRegimenDetailRequest {

    private Long regimenDetailId;

    private String medicineName;
    
    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String firstTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String secondTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String thirdTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String fourthTime;
}
