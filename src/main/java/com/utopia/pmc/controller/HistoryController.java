package com.utopia.pmc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.request.history.HistoryRequest;
import com.utopia.pmc.data.dto.response.history.HistoryResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.services.history.HistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @Operation(summary = "Create history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create history successfull."),
            @ApiResponse(responseCode = "404", description = "Regimen not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @PostMapping
    public ResponseEntity<Void> createHistory(@Valid @RequestBody HistoryRequest historyRequest) {
        historyService.createHistory(historyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get history of current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HistoryResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @GetMapping()
    public ResponseEntity<List<HistoryResponse>> getHistoryOfUser() {
        return ResponseEntity.status(HttpStatus.OK).body(
                historyService.getHistoryByUser());
    }
}
