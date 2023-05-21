package com.utopia.pmc.data.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.pmc.data.entities.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    public List<Medicine> findByIdIn(Set<Long> id);
}
