package com.utopia.pmc.data.repositories.medicine;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.entities.medicine.DosageForm;

@Repository
public interface DosageFormRepository extends JpaRepository<DosageForm, Long>{
    public Optional<DosageForm> findByFormIgnoreCase(String form);
}
