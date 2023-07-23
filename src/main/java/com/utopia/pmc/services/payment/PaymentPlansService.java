package com.utopia.pmc.services.payment;

import java.util.List;

import com.utopia.pmc.data.dto.request.payment.NewPaymentRequest;
import com.utopia.pmc.data.dto.response.payment.PaymentPlanResponse;
import com.utopia.pmc.data.entities.User;

public interface PaymentPlansService {
    public void checkUserPlan(User user);
    public List<PaymentPlanResponse> getAllPaymentPlans();
    public void createNewPaymentPlan(NewPaymentRequest newPaymentRequest);
}
