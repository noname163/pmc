package com.utopia.pmc.data.entities.medicine;

import javax.persistence.EmbeddedId;
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
    @EmbeddedId
    private UseOfMedicineKey id;

    @ManyToOne
    @MapsId("medicineId")
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
    
    @ManyToOne
    @MapsId("medicationUseId")
    @JoinColumn(name = "medication_use_id")
    private MedicationUse medicationUse;
}

