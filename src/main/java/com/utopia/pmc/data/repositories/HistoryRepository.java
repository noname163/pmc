package com.utopia.pmc.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.entities.History;

@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {
    
}
