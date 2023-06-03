package com.utopia.pmc.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.User;

@Repository
public interface RegimenRepository extends JpaRepository<Regimen, Long> {
    public List<Regimen> findByStatus(RegimentStatus regimentStatus);
    public List<Regimen> findByUser(User user);
    public Integer countByUserId(Long userId);
}
