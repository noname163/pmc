package com.utopia.pmc.services.historyDetail.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.dto.response.history.HistoryDetailRespone;
import com.utopia.pmc.data.entities.History;
import com.utopia.pmc.data.entities.HistoryDetail;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.HistoryDetailRepository;
import com.utopia.pmc.data.repositories.RegimenDetailRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.HistoryDetailMapper;
import com.utopia.pmc.services.authenticate.SecurityContextService;
import com.utopia.pmc.services.historyDetail.HistoryDetailService;
import com.utopia.pmc.services.regimenDetail.RegimenDetailService;

@Service
public class HistoryDetailServiceImpl implements HistoryDetailService {
    @Autowired
    private HistoryDetailRepository historyDetailRepository;
    @Autowired
    private RegimenDetailRepository regimenDetailRepository;
    @Autowired
    private RegimenDetailService regimenDetailService;
    @Autowired
    private HistoryDetailMapper historyDetailMapper;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private Message message;

    @Override
    @Transactional
    public void createHistoryDetail(History history, Set<Long> medicineIds) {
        List<RegimenDetail> regimenDetails = regimenDetailRepository
                .findByRegimenIdAndMedicineIdIn(history.getRegiment().getId(), medicineIds);

        if (regimenDetails == null || regimenDetails.isEmpty()) {
            throw new BadRequestException(message.emptyList("Medicine"));
        }

        List<HistoryDetail> historyDetails = new ArrayList<>();

        for (RegimenDetail regimenDetail : regimenDetails) {
            historyDetails.add(HistoryDetail
                    .builder()
                    .regimenDetail(regimenDetail)
                    .history(history)
                    .build());
        }

        historyDetailRepository.saveAll(historyDetails);
        regimenDetailService.reduceMedicineQuantity(history.getRegiment().getId(), medicineIds);
    }

    @Override
    public List<HistoryDetailRespone> getHistoryDetailRespones(Long historyId) {
        User user = securityContextService.getCurrentUser();
        List<HistoryDetail> historyDetails = historyDetailRepository.findByHistoryId(historyId);
        if (historyDetails == null || historyDetails.isEmpty()) {
            throw new BadRequestException(message.emptyList("History Detail"));
        }
        if (user.getId() != historyDetails.get(0).getHistory().getUser().getId()) {
            throw new BadRequestException(message.invalidUser());
        }
        return historyDetailMapper.mapEntityToDtos(historyDetails);
    }

}
