package com.utopia.pmc.services.history.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.utopia.pmc.data.dto.request.history.HistoryRequest;
import com.utopia.pmc.data.dto.response.history.HistoryResponse;
import com.utopia.pmc.data.entities.History;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.HistoryRepository;
import com.utopia.pmc.data.repositories.RegimenRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.HistoryMapper;
import com.utopia.pmc.services.authenticate.SecurityContextService;
import com.utopia.pmc.services.history.HistoryService;
import com.utopia.pmc.services.historyDetail.HistoryDetailService;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private RegimenRepository regimenRepository;
    @Autowired
    private HistoryDetailService historyDetailService;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private Message message;

    @Override
    @Transient
    public void createHistory(@RequestBody HistoryRequest historyRequest) {
        User user = securityContextService.getCurrentUser();
        if(user == null){
            throw new BadRequestException(message.invalidUser());
        }
        Regimen regimen = regimenRepository
                .findById(historyRequest.getRegimentId())
                .orElseThrow(() -> new BadRequestException(
                        message.objectNotFoundByIdMessage("Regimen", historyRequest.getRegimentId())));
        History history = historyMapper.mapDtoToEntity(historyRequest);
        history.setRegiment(regimen);
        history.setUser(user);
        history.setTotalMedicine(regimen.getRegimentDetails().size());
        history.setDateTaken(LocalDate.now());
        history.setTimeTaken(LocalTime.now());
        history = historyRepository.save(history);
        historyDetailService.createHistoryDetail(history, historyRequest.getMedicineIds());
    }

    @Override
    public List<HistoryResponse> getHistoryByUser() {
        User user = securityContextService.getCurrentUser();
        if(user == null){
            throw new BadRequestException(message.invalidUser());
        }
        List<History> histories = historyRepository.findByUserIdOrderByDateTakenDesc(user.getId());

        if (histories.isEmpty()) {
            throw new BadRequestException(message.emptyList("History"));
        }

        return historyMapper.mapEntityToDtos(histories);
    }

}
