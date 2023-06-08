package com.utopia.pmc.services.regimen.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.constants.statuses.TakenStatus;
import com.utopia.pmc.data.database.DailyData;
import com.utopia.pmc.data.dto.request.regimen.EditRegimenRequest;
import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.RegimenRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.RegimenMapper;
import com.utopia.pmc.services.authenticate.SecurityContextService;
import com.utopia.pmc.services.payment.PaymentPlansService;
import com.utopia.pmc.services.regimen.RegimenService;
import com.utopia.pmc.services.regimenDetail.RegimenDetailService;

@Service
public class RegimenServiceImpl implements RegimenService {

    @Autowired
    private RegimenRepository regimentRepository;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private RegimenMapper regimentMapper;
    @Autowired
    private RegimenDetailService regimentDetailService;
    @Autowired
    private PaymentPlansService paymentPlansService;
    @Autowired
    private Message message;

    @Transactional
    @Override
    public RegimenResponse createRegiment(RegimenRequest regimentRequest) {
        User user = securityContextService.getCurrentUser();
        paymentPlansService.checkUserPlan(user);

        Regimen regiment = regimentMapper.mapDtoToEntity(regimentRequest);
        RegimentStatus status = RegimentStatus.ENABLE;
        if (regimentRequest.getStartNow() == true) {
            regiment.setCreatedDate(LocalDate.now());
            status = RegimentStatus.INPROCESS;
        }
        regiment.setUser(user);
        regiment.setStatus(status);

        regiment = regimentRepository.save(regiment);
        regimentRequest.setId(regiment.getId());
        regimentDetailService.createRegimentDetails(regimentRequest);

        Regimen result = regimentRepository.findById(regiment.getId())
                .orElseThrow(() -> new BadRequestException("Errors as create new regiment"));

        return regimentMapper.mapEntityToDtoRegimenResponse(result);
    }

    @Override
    public void updateRegiment(Long regimentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRegiment'");
    }

    @Override
    public List<RegimenResponse> getRegimenOfCurrentUser() {
        User user = securityContextService.getCurrentUser();
        securityContextService.validateCurrentUser(user);

        List<Regimen> regimens = regimentRepository.findByUser(user);

        if (regimens.isEmpty()) {
            throw new BadRequestException(message.emptyList("Regimen"));
        }
        return regimentMapper.mapEntitiesToDtoRegimenResponse(regimens);
    }

    @Override
    public void editRegimen(EditRegimenRequest editRegimenRequest) {

        Regimen regimen = regimentRepository.findById(editRegimenRequest.getRegimenId())
                .orElseThrow(() -> new BadRequestException(
                        message.objectNotFoundByIdMessage("Regimen", editRegimenRequest.getRegimenId())));
        User userOfRegimen = regimen.getUser();
        securityContextService.validateCurrentUser(userOfRegimen);

        if (regimen.getStatus().equals(RegimentStatus.INPROCESS)) {
            regimen.setImage(editRegimenRequest.getImage());
            regimen.setName(editRegimenRequest.getRegimenName());
        } else {
            LocalDate startDate = editRegimenRequest.getStartDate();
            Integer doseRegimen = editRegimenRequest.getDosageRegimen();
            Period period = editRegimenRequest.getPeriod();

            regimen.setImage(editRegimenRequest.getImage());
            regimen.setName(editRegimenRequest.getRegimenName());
            regimen.setStartDate(startDate != null ? startDate : regimen.getStartDate());
            regimen.setDoseRegiment(doseRegimen != null ? doseRegimen : regimen.getDoseRegiment());
            regimen.setPeriod(period != null ? period : regimen.getPeriod());
        }
        regimentRepository.save(regimen);

    }

    @Override
    public void countTakenTimeOrMissedTime(Regimen regimen, TakenStatus takenStatus) {
        if (takenStatus.equals(TakenStatus.TAKEN)) {
            int previousTakenTime = regimen.getTakenTime();
            regimen.setTakenTime(previousTakenTime + 1);
        }
        int previousMissedTime = regimen.getMissedTime();
        regimen.setMissedTime(previousMissedTime + 1);

        regimentRepository.save(regimen);
    }

}
