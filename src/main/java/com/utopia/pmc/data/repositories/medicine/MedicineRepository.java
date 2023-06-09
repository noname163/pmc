package com.utopia.pmc.data.repositories.medicine;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.pmc.data.entities.medicine.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    public List<Medicine> findByIdIn(Set<Long> id);
    public Optional<List<Medicine>> findByNameContainingIgnoreCase(String name);
    public Optional<Medicine> findByName(String name);
}
