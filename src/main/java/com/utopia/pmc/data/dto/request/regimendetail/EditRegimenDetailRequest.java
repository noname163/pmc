package com.utopia.pmc.data.dto.request.regimendetail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utopia.pmc.data.constants.others.Validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EditRegimenDetailRequest {

    @NotNull(message = "Regimen detail is required")
    private Long regimenDetailId;

    @NotBlank(message = "Medicine name is required")
    private String medicineName;

    @NotBlank(message = "Medicine form is required")
    private String medicineForm;
    
    @NotBlank(message = "Medicine image is required")
    private String medicineUrl;
    
    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String firstTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String secondTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String thirdTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String fourthTime;
}
