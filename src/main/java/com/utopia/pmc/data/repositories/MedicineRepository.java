package com.utopia.pmc.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.pmc.data.entities.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    
}
