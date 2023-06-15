package com.utopia.pmc.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.pmc.data.entities.PaymentPlan;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long>{
    Optional<PaymentPlan> findByName(String name);
}
