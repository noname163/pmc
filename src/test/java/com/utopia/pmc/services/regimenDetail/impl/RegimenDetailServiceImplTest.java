package com.utopia.pmc.services.regimenDetail.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.utopia.pmc.data.constants.others.Validation;
import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.data.repositories.RegimenDetailRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.RegimenDetailMapper;
import com.utopia.pmc.utils.RegimenFunction;

public class RegimenDetailServiceImplTest {

    private RegimenDetailRepository regimenDetailRepository;
    private RegimenDetailMapper regimenDetailMapper;
    private RegimenDetailServiceImpl regimenDetailServiceImpl;
    private RegimenFunction regimenFunction;
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
        regimenFunction = mock(RegimenFunction.class);
        regimenDetailRepository = mock(RegimenDetailRepository.class);
        message = mock(Message.class);
        regimenDetailServiceImpl = RegimenDetailServiceImpl
                .builder()
                .regimenFunction(regimenFunction)
                .regimentDetailRepository(regimenDetailRepository)
                .message(message)
                .regimentDetailMapper(regimenDetailMapper)
                .build();
    }

    @Test
    void testCreateRegimentDetails() {

    }

    @Test
    void testGetRegimentDetailResponsesByStatusAndTime_WhenDataValid_ShouldReturnMap() {
        // Mock input data
        RegimentStatus regimentStatus = RegimentStatus.INPROCESS;
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusMinutes(5);
        List<RegimenDetail> regimentDetails = new ArrayList<>();
        regimentDetails.add(regimenDetail);
        // Add mock RegimentDetail objects to the list
        when(regimenDetailRepository.findByStatusAndTime(regimentStatus, startTime, endTime))
                .thenReturn(regimentDetails);

        // Mock mapping of entity to DTO
        RegimenDetailResponse mockResponse = RegimenDetailResponse.builder().build();
        when(regimenDetailMapper.mapEntityToDto(any(RegimenDetail.class))).thenReturn(mockResponse);

        LocalTime takenTime = LocalTime.of(12, 0); // Set a specific time for testing
        when(regimenFunction.determineTakenTime(any(RegimenDetail.class))).thenReturn(takenTime);
        
        // Execute the method
        Map<String, List<RegimenDetailResponse>> result = regimenDetailServiceImpl
                .getRegimentDetailResponsesByStatusAndTime(
                        regimentStatus, startTime, endTime);

        // Verify repository method invocation
        verify(regimenDetailRepository).findByStatusAndTime(regimentStatus, startTime, endTime);

        // Verify mapping of entity to DTO
        verify(regimenDetailMapper, times(regimentDetails.size())).mapEntityToDto(any(RegimenDetail.class));

        // Assert the result
        assertNotNull(result);
        // Add more assertions based on your expectations
    }

    @Test
    void testGetRegimentDetailResponsesByStatusAndTime_WhenNoDataFound_ShouldThrowBadRequestException() {
        // Mock input data
        RegimentStatus regimentStatus = RegimentStatus.INPROCESS;
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusMinutes(5);
        List<RegimenDetail> emptyRegimentDetails = new ArrayList<>();
        when(regimenDetailRepository.findByStatusAndTime(regimentStatus, startTime, endTime))
                .thenReturn(emptyRegimentDetails);

        // Execute and assert
        assertThrows(BadRequestException.class, () -> regimenDetailServiceImpl
                .getRegimentDetailResponsesByStatusAndTime(regimentStatus, startTime, endTime));

        // Verify repository method invocation
        verify(regimenDetailRepository).findByStatusAndTime(regimentStatus, startTime, endTime);
    }

}
