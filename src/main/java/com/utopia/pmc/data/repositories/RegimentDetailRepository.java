package com.utopia.pmc.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.pmc.data.entities.RegimentDetail;

public interface RegimentDetailRepository extends JpaRepository<RegimentDetail, Long> {
    
}
