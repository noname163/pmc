package com.utopia.pmc.services.regimen.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.database.DailyData;
import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.RegimenRepository;
import com.utopia.pmc.exceptions.BadRequestException;
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

    @Transactional
    @Override
    public RegimenResponse createRegiment(RegimenRequest regimentRequest) {
        User user = securityContextService.getCurrentUser();
        paymentPlansService.checkUserPlan(user);

        Regimen regiment = regimentMapper.mapDtoToEntity(regimentRequest);

        regiment.setUser(user);
        regiment.setCreatedDate(LocalDate.now());
        regiment.setStatus(
                Boolean.TRUE.equals(regimentRequest.getStartNow()) ? RegimentStatus.INPROCESS : RegimentStatus.ENABLE);

        regiment = regimentRepository.save(regiment);
        regimentRequest.setId(regiment.getId());
        regimentDetailService.createRegimentDetails(regimentRequest);

        Regimen result = regimentRepository.findById(regiment.getId())
                .orElseThrow(() -> new BadRequestException("Errors as create new regiment"));

        DailyData.addRegimen(result);
        return regimentMapper.mapEntityToDtoRegimenResponse(result);
    }

    @Override
    public void updateRegiment(Long regimentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRegiment'");
    }

}
