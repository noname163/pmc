package com.utopia.pmc.data.repositories;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.entities.RegimentDetail;

public interface RegimentDetailRepository extends JpaRepository<RegimentDetail, Long> {

    @Query("SELECT rd FROM RegimentDetail rd" +
            " JOIN FETCH rd.regiment r" +
            " WHERE r.status = :regimentStatus" +
            " AND (" +
            "    rd.firstTime BETWEEN :startTime AND :endTime" +
            "    OR rd.secondTime BETWEEN :startTime AND :endTime" +
            "    OR rd.thirdTime BETWEEN :startTime AND :endTime" +
            "    OR rd.fourthTime BETWEEN :startTime AND :endTime" +
            " )"
            )
    List<RegimentDetail> findByStatusAndTime(
            @Param("regimentStatus") RegimentStatus regimentStatus,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);

}
