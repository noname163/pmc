package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.response.payment.PaymentPlanResponse;
import com.utopia.pmc.data.dto.response.payment.PaymentResponse;
import com.utopia.pmc.data.entities.PaymentPlan;

@Component
public class PaymentPlanMapper {
    public PaymentPlanResponse mapEntityToDto(PaymentPlan paymentPlan){
        return PaymentPlanResponse
        .builder()
        .id(paymentPlan.getId())
        .name(paymentPlan.getName())
        .money(paymentPlan.getMoney())
        .numberOfMedicine(paymentPlan.getNumberOfMedicine())
        .numberOfRegiment(paymentPlan.getNumberOfRegiment())
        .expriredTime(paymentPlan.getExpriredTime())
        .period(paymentPlan.getPeriod())
        .build();
    }

    public List<PaymentPlanResponse> mapEntitiesToDtos(List<PaymentPlan> paymentPlans){
        return paymentPlans.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }
}
