package com.utopia.pmc.services.historyDetail.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.database.DailyData;
import com.utopia.pmc.data.entities.History;
import com.utopia.pmc.data.entities.HistoryDetail;
import com.utopia.pmc.data.entities.medicine.Medicine;
import com.utopia.pmc.data.repositories.HistoryDetailRepository;
import com.utopia.pmc.data.repositories.medicine.MedicineRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.services.historyDetail.HistoryDetailService;
import com.utopia.pmc.services.regimenDetail.RegimenDetailService;

@Service
public class HistoryDetailServiceImpl implements HistoryDetailService {
    @Autowired
    private HistoryDetailRepository historyDetailRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private RegimenDetailService regimenDetailService;
    @Autowired
    private Message message;

    @Override
    @Transactional
    public void createHistoryDetail(History history, Set<Long> medicineIds) {
        List<Medicine> medicines = medicineRepository.findByIdIn(medicineIds);
        if (medicines == null || medicines.isEmpty()) {
            throw new BadRequestException(message.emptyList("Medicine"));
        }
        List<HistoryDetail> historyDetails = new ArrayList<>();
        for (Medicine medicine : medicines) {
            historyDetails.add(HistoryDetail
                    .builder()
                    .medicine(medicine)
                    .history(history)
                    .build());
        }
        historyDetailRepository.saveAll(historyDetails);
        regimenDetailService.reduceMedicineQuantity(history.getRegiment().getId(), medicineIds);
        DailyData.removeRegimenResponse(history.getRegiment().getDeviceToken(), medicineIds);
    }

}
