package com.utopia.pmc.data.dto.response.regimendetail;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utopia.pmc.data.constants.statuses.NotificationStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimenDetailResponse {
    private Long regimenId;
    
    private Long regimenDetailId;
    
    private Integer takenQuantity;

    private Integer numberOfMedicine;

    private LocalTime firstTime;

    private LocalTime secondTime;

    private LocalTime thirdTime;

    private LocalTime fourthTime;

    private Long medicineId;

    private String medicineName;

    private String medicineForm;
    
    private String medicineUrl;

    private String medicineNote;
    @JsonIgnore
    private String deviceToken;
    @JsonIgnore
    private String regimenName;
    @JsonIgnore
    private NotificationStatus notificationStatus;
}
