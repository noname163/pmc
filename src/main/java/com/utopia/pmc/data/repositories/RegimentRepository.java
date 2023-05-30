package com.utopia.pmc.data.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.entities.Regiment;
import com.utopia.pmc.data.entities.User;

@Repository
public interface RegimentRepository extends JpaRepository<Regiment, Long> {
    public List<Regiment> findByStatus(RegimentStatus regimentStatus);
    public List<Regiment> findByUser(User user);
    public Integer countByUserId(Long userId);
}
