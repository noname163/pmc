package com.utopia.pmc.data.dto.request.regimendetail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utopia.pmc.data.constants.others.Validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegimenDetailRequest {

    @NotNull(message = "Taken quantity is required")
    private Integer takenQuantity;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String firstTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String secondTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String thirdTime;

    @JsonFormat(pattern = Validation.TIME_FORMAT)
    private String fourthTime;

    @NotBlank(message = "Medicine name is required")
    private String medicineName;

    @NotBlank(message = "Medicine form is required")
    private String medicineForm;
    
    @NotBlank(message = "Medicine url is required")
    private String medicineUrl;

    @JsonIgnore
    private Long regimentId;

    private Long medicineId;
}
