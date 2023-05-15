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

import com.utopia.pmc.data.constants.Dose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Regiment_Detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegimentDetail {
    @Id
    @SequenceGenerator(name = "regiment_detail_sequence", sequenceName = "regiment_detail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "regiment_detail_sequence")
    private long id;
    @Column
    private Integer quantity;
    @Column
    private Dose dose;
    @Column
    private LocalTime time;
    @ManyToOne
    @JoinColumn(name = "regiment_id")
    private Regiment regiment;
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
}
