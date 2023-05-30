package com.utopia.pmc.data.entities;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.utopia.pmc.data.constants.others.Dose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Regiment_Detail")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegimenDetail {
    @Id
    @SequenceGenerator(name = "regiment_detail_sequence", sequenceName = "regiment_detail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "regiment_detail_sequence")
    private long id;
    @Column(name = "taken_quantity")
    private Integer takenQuantity;
    @Column(name = "dose")
    private Dose dose;
    @Column(name =  "number_of_medicine")
    private Integer numberOfMedicine;
    @Column(name = "first_time")
    private LocalTime firstTime;
    @Column(name = "second_time")
    private LocalTime secondTime;
    @Column(name = "third_time")
    private LocalTime thirdTime;
    @Column(name = "fourth_time")
    private LocalTime fourthTime;
    @ManyToOne
    @JoinColumn(name = "regiment_id")
    private Regimen regiment;
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
}
