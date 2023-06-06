package com.utopia.pmc.data.entities.medicine;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DosageForm")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DosageForm {
    @Id
    @SequenceGenerator(name = "dosage_form_sequence", sequenceName = "dosage_form_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "dosage_form_sequence")
    private long id;
    @Column(name = "form")
    private String form;
    @OneToMany(mappedBy = "dosageForm")
    private List<Medicine> medicines;
}
