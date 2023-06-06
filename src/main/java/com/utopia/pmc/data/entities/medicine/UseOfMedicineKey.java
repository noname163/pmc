package com.utopia.pmc.data.entities.medicine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UseOfMedicineKey implements Serializable {
    @Column(name = "medicine_id")
    private Long medicineId;

    @Column(name = "medication_use_id")
    private Long medicationUseId;
}

