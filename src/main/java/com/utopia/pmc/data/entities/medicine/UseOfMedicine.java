package com.utopia.pmc.data.entities.medicine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UseOfMedication")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UseOfMedicine {
    @Id
    @SequenceGenerator(name = "use_of_medicine_sequence", sequenceName = "use_of_medicine_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "use_of_medicine_sequence")
    private long id;

    @ManyToOne
    @MapsId("medicine_id")
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
    @ManyToOne
    @MapsId("medication_use")
    @JoinColumn(name = "medication_use_id")
    private MedicationUse medicationUse;
}
