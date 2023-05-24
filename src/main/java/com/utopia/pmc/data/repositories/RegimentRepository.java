package com.utopia.pmc.data.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.entities.Regiment;

public interface RegimentRepository extends JpaRepository<Regiment, Long> {
    public List<Regiment> findByStatus(RegimentStatus regimentStatus);
}
