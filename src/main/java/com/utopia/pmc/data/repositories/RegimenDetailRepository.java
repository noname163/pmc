package com.utopia.pmc.data.repositories;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;

@Repository
public interface RegimenDetailRepository extends JpaRepository<RegimenDetail, Long> {

        public List<RegimenDetail> findByRegimentIn(List<Regimen> regiments);

        @Query("SELECT rd FROM RegimenDetail rd" +
                        " JOIN FETCH rd.regiment r" +
                        " WHERE r.status = :regimentStatus" +
                        " AND (" +
                        "    rd.firstTime BETWEEN :startTime AND :endTime" +
                        "    OR rd.secondTime BETWEEN :startTime AND :endTime" +
                        "    OR rd.thirdTime BETWEEN :startTime AND :endTime" +
                        "    OR rd.fourthTime BETWEEN :startTime AND :endTime" +
                        " )")
        List<RegimenDetail> findByStatusAndTime(
                        @Param("regimentStatus") RegimentStatus regimentStatus,
                        @Param("startTime") LocalTime startTime,
                        @Param("endTime") LocalTime endTime);

}
