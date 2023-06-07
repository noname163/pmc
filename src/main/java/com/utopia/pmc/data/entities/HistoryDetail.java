package com.utopia.pmc.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.utopia.pmc.data.entities.medicine.Medicine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HistoryDetail")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDetail {
    @Id
    @SequenceGenerator(name = "history_detail_sequence", sequenceName = "history_detail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "history_detail_sequence")
    private long id;
    @ManyToOne
    @JoinColumn(name = "regimen_detail_id")
    private RegimenDetail regimenDetail;
    @ManyToOne
    @JoinColumn(name = "history_id")
    private History history;
}
