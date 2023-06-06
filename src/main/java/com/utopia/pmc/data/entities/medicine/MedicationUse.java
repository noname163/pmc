package com.utopia.pmc.data.entities.medicine;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "MedicationUse")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicationUse {
    @Id
    @SequenceGenerator(name = "medication_use_sequence", sequenceName = "medication_use_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "medication_use_sequence")
    private long id;

    @Column(name = "use")
    private String use;

    @Column(name = "describe", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "medicationUse", cascade = CascadeType.ALL)
    private List<UseOfMedicine> useOfMedicines;
}
