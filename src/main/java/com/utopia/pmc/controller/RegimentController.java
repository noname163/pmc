package com.utopia.pmc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.services.regimen.RegimenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/regimen")
public class RegimentController {
    @Autowired
    private RegimenService regimentService;

    @Operation(summary = "Create new regiment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegimenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Regiment information not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @PostMapping()
    public ResponseEntity<RegimenResponse> createRegiment(@Valid @RequestBody RegimenRequest regimentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                regimentService.createRegiment(regimentRequest));
    }
}
