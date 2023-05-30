package com.utopia.pmc.data.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.utopia.pmc.data.constants.others.ConsumerWay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Medicine")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
    @Id
    @SequenceGenerator(name = "medicine_sequence", sequenceName = "medicine_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "medicine_sequence")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "expried_tiem")
    private Integer expiredTime;
    @Column(name = "consumer_way")
    private ConsumerWay consumerWay;
    @Column(name = "describe")
    // @Lob
    private String describe;
    @OneToMany(mappedBy = "medicine")
    private List<RegimenDetail> regimenDetails;
    @OneToMany(mappedBy = "medicine")
    private List<History> histories;
}
