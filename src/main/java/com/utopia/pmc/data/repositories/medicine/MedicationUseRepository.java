package com.utopia.pmc.data.repositories.medicine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.entities.medicine.MedicationUse;

@Repository
public interface MedicationUseRepository extends JpaRepository<MedicationUse, Long> {

}
