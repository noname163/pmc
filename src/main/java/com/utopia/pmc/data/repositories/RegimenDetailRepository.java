package com.utopia.pmc.data.repositories;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;

@Repository
public interface RegimenDetailRepository extends JpaRepository<RegimenDetail, Long> {

        public List<RegimenDetail> findByRegimenIn(List<Regimen> regiments);

        public List<RegimenDetail> findByRegimenIdAndMedicineIdIn(Long regimenId, Set<Long> medicineId);

        @Query("SELECT rd FROM RegimenDetail rd" +
                        " JOIN FETCH rd.regimen r" +
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

        List<RegimenDetail> findByRegimenId(Long id);
}
