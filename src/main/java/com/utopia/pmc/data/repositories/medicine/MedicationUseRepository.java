package com.utopia.pmc.data.repositories.medicine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.entities.medicine.MedicationUse;

@Repository
public interface MedicationUseRepository extends JpaRepository<MedicationUse, Long> {
    public List<MedicationUse> findByIdIn(List<Long> id);
    
}
