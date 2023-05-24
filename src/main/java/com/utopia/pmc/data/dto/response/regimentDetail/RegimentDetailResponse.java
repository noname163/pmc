package com.utopia.pmc.data.dto.response.regimentDetail;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegimentDetailResponse {
    private Long regimentId;
    private Integer quantity;
    private String dose;
    private LocalTime takenTime;
    private String medicineImage;
    private String medicinename;
}
