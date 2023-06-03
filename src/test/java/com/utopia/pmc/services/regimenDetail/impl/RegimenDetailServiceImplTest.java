package com.utopia.pmc.services.regimenDetail.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.data.repositories.RegimenDetailRepository;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.RegimenDetailMapper;

public class RegimenDetailServiceImplTest {

    private RegimenDetailRepository regimenDetailRepository;
    private RegimenDetailMapper regimenDetailMapper;
    private RegimenDetailServiceImpl regimenDetailServiceImpl;
    private Message message;
    private LocalTime startTime;
    private LocalTime endTime;
    private RegimentStatus regimentStatus;
    private RegimenDetail regimenDetail;
    private Regimen regimen;

    @BeforeEach
    void setUp() {
        startTime = LocalTime.now();
        endTime = startTime.plusMinutes(5);
        regimentStatus = RegimentStatus.INPROCESS;
        regimenDetail = RegimenDetail.builder().build();
        regimen = Regimen.builder().deviceToken("somthing").build();
        regimenDetailMapper = mock(RegimenDetailMapper.class);
        regimenDetailRepository = mock(RegimenDetailRepository.class);
        message = mock(Message.class);
        regimenDetailServiceImpl = RegimenDetailServiceImpl
                .builder()
                .regimentDetailRepository(regimenDetailRepository)
                .message(message)
                .regimentDetailMapper(regimenDetailMapper)
                .build();
    }

    @Test
    void testCreateRegimentDetails() {

    }

    @Test
    void getRegimentDetailResponsesByStatusAndTime_WhenDataValid_ShouldReturnMap() {

        RegimenDetailResponse regimenDetailResponse = mock(RegimenDetailResponse.class);
        RegimenDetail regimenDetailMock = mock(RegimenDetail.class);
        List<RegimenDetail> regimentDetails = new ArrayList<>();
        regimentDetails.add(regimenDetailMock);
        List<RegimenDetailResponse> regimenDetailResponses = mock(List.class);
        regimenDetailResponses.add(regimenDetailResponse);
        Map<String, List<RegimenDetailResponse>> result = mock(Map.class);

        when(regimenDetailRepository.findByStatusAndTime(regimentStatus, startTime, endTime))
                .thenReturn(regimentDetails);
        when(regimenDetailMock.getRegimen()).thenReturn(regimen);
        when(result.get(regimen.getDeviceToken())).thenReturn(regimenDetailResponses);
        when(regimenDetailMapper.mapEntityToDto(regimenDetail)).thenReturn(regimenDetailResponse);
        when(result.put(regimen.getDeviceToken(), regimenDetailResponses)).thenReturn(regimenDetailResponses);
        Map<String, List<RegimenDetailResponse>> actual = regimenDetailServiceImpl
                .getRegimentDetailResponsesByStatusAndTime(regimentStatus, startTime, endTime);

        verify(regimenDetailResponses).add(regimenDetailResponse);
        assertNotNull(actual);
    }
}
