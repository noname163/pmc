package com.utopia.pmc.data.dto.request.regimendetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utopia.pmc.data.constants.others.Dose;
import com.utopia.pmc.data.constants.others.Validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegimenDetailRequest {

    private Integer takenQuantity;

    private Dose dose;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String firstTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String secondTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String thirdTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String fourthTime;

    @JsonIgnore
    private Long regimentId;

    private Long medicineId;
}
