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
@Table(name = "MedicineClassification")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicineClassification {
    @Id
    @SequenceGenerator(name = "medicine_classification_sequence", sequenceName = "medicine_classification_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "medicine_classification_sequence")
    private long id;

    private String classification;

    @Column(name = "describe", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "classification")
    private List<Medicine> medicines;
}
