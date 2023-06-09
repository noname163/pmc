package com.utopia.pmc.data.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Regimen {
    @Id
    @SequenceGenerator(name = "regiment_sequence", sequenceName = "regiment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "regiment_sequence")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name ="image")
    private String image;
    @Column(name = "dose_regiment")
    private Integer doseRegiment;
    @Column(name = "taken_time")
    private Integer takenTime;
    @Column(name = "missed_time")
    private Integer missedTime;
    @Column(name = "peroid")
    private Period period; 
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RegimentStatus status;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name="device_token")
    private String deviceToken;
    @Column(name = "is_alert")
    private Boolean isAlert;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "regimen")
    private List<RegimenDetail> regimentDetails;
    @OneToMany(mappedBy = "regiment")
    private List<History> histories;
}
