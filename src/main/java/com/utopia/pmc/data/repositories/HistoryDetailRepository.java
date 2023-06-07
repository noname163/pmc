package com.utopia.pmc.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.entities.HistoryDetail;

@Repository
public interface HistoryDetailRepository extends JpaRepository<HistoryDetail, Long> {
    public List<HistoryDetail> findByHistoryId(Long historyId);
}
