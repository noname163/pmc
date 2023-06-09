package com.utopia.pmc.data.entities;

import java.time.LocalDate;
import java.time.LocalTime;
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

import com.utopia.pmc.data.constants.statuses.TakenStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "History")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @SequenceGenerator(name = "history_sequence", sequenceName = "history_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "history_sequence")
    private long id;
    @Column
    private LocalDate dateTaken;
    @Column
    private LocalTime timeTaken;
    @Column
    private TakenStatus takenStatus;
    @Column
    private Integer totalMedicine;
    @Column
    private Integer numberOfTaken;
    @ManyToOne
    @JoinColumn(name = "regiment_id")
    private Regimen regiment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; 
    @OneToMany(mappedBy = "history")
    private List<HistoryDetail> historyDetails;
}
