package com.utopia.pmc.data.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.utopia.pmc.data.constants.others.Period;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PaymentPlan")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPlan {
    @Id
    @SequenceGenerator(name = "payment_plan_sequence", sequenceName = "payment_plan_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payment_plan_sequence")
    private long id;

    private String name;
    private Double money;
    private Integer numberOfRegiment;
    private Integer numberOfMedicine;
    private Integer expriredTime;
    private Period period;
    @OneToMany(mappedBy = "paymentPlan")
    private List<User> users;
}
