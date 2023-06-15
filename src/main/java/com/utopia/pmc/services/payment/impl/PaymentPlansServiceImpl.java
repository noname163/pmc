package com.utopia.pmc.services.payment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.dto.response.payment.PaymentPlanResponse;
import com.utopia.pmc.data.entities.PaymentPlan;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.PaymentPlanRepository;
import com.utopia.pmc.data.repositories.RegimenDetailRepository;
import com.utopia.pmc.data.repositories.RegimenRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.PaymentPlanMapper;
import com.utopia.pmc.services.payment.PaymentPlansService;

@Service
public class PaymentPlansServiceImpl implements PaymentPlansService {
    @Autowired
    private RegimenRepository regimentRepository;
    @Autowired
    private RegimenDetailRepository regimentDetailRepository;
    @Autowired
    private PaymentPlanRepository paymentPlanRepository;
    @Autowired
    private PaymentPlanMapper paymentPlanMapper;
    @Autowired
    private Message message;

    @Override
    public void checkUserPlan(User user) {
        List<Regimen> regiments = regimentRepository.findByUser(user);
        List<RegimenDetail> regimentDetails = regimentDetailRepository.findByRegimenIn(regiments);
        Integer totalOfRegiment = regiments.size();
        Integer totalMedicine = regimentDetails.size();

        PaymentPlan userPaymentPlan = user.getPaymentPlan();
        String message = "You have used up all the resources allocated by the plan";
        if (userPaymentPlan == null && totalOfRegiment > 1) {
            throw new BadRequestException(message);
        }
        if (userPaymentPlan == null && totalMedicine >= 10) {
            throw new BadRequestException(message);
        }
        if (userPaymentPlan != null && totalOfRegiment >= userPaymentPlan.getNumberOfRegiment()) {
            throw new BadRequestException(message);
        }
        if (userPaymentPlan != null && totalMedicine >= userPaymentPlan.getNumberOfMedicine()) {
            throw new BadRequestException(message);
        }
    }

    @Override
    public List<PaymentPlanResponse> getAllPaymentPlans() {
        List<PaymentPlan> paymentPlans = paymentPlanRepository.findAll();
        if (paymentPlans.isEmpty()) {
            throw new BadRequestException(message.emptyList("Payment Plant"));
        }
        return paymentPlanMapper.mapEntitiesToDtos(paymentPlans);

    }
}
