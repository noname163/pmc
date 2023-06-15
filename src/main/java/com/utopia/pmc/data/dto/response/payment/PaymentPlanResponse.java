package com.utopia.pmc.data.dto.response.payment;

import com.utopia.pmc.data.constants.others.Period;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentPlanResponse {
    private Long id;
    private String name;
    private Double money;
    private Integer numberOfRegiment;
    private Integer numberOfMedicine;
    private Integer expriredTime;
    private Period period;
}
