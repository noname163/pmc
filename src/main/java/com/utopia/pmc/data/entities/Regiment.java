package com.utopia.pmc.data.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.constants.statuses.RegimentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Regiment")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Regiment {
    @Id
    @SequenceGenerator(name = "regiment_sequence", sequenceName = "regiment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "regiment_sequence")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "dose_regiment")
    private Integer doseRegiment;
    @Column(name = "peroid")
    private Period period; 
    @Column(name = "status")
    private RegimentStatus status;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "regiment")
    private List<RegimentDetail> regimentDetails;
    @OneToMany(mappedBy = "regiment")
    private List<History> histories;
}
