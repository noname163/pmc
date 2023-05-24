package com.utopia.pmc.services.regiment.impl;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.request.RegimentRequest;
import com.utopia.pmc.data.entities.Regiment;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.RegimentRepository;
import com.utopia.pmc.mappers.RegimentMapper;
import com.utopia.pmc.services.authenticate.SecurityContextService;
import com.utopia.pmc.services.regiment.RegimentService;
import com.utopia.pmc.services.regimentDetail.RegimentDetailService;

@Service
public class RegimentServiceImpl implements RegimentService {

    @Autowired
    private RegimentRepository regimentRepository;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private RegimentMapper regimentMapper;
    @Autowired
    private RegimentDetailService regimentDetailService;

    @Transactional
    @Override
    public void createRegiment(RegimentRequest regimentRequest) {
        User user = securityContextService.getCurrentUser();
        Regiment regiment = regimentMapper.mapDtoToEntity(regimentRequest);
        regiment.setUser(user);
        regiment.setCreatedDate(LocalDate.now());
        regiment.setStatus(Boolean.TRUE.equals(regimentRequest.getStartNow()) ? RegimentStatus.INPROCESS : RegimentStatus.ENABLE);
        regiment = regimentRepository.save(regiment);
        regimentRequest.setId(regiment.getId());
        regimentDetailService.createRegimentDetails(regimentRequest);
    }

    @Override
    public void updateRegiment(Long regimentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRegiment'");
    }

}
