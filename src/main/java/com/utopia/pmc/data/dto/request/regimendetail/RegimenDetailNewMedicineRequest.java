package com.utopia.pmc.data.dto.request.regimendetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utopia.pmc.data.constants.others.Validation;
import com.utopia.pmc.data.dto.request.medicine.UserMedicineRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimenDetailNewMedicineRequest {
    private UserMedicineRequest userMedicineRequest;
    private RegimenDetailRequest regimenDetailRequest;
}
