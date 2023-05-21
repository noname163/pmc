package com.utopia.pmc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.request.RegimentRequest;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.services.regiment.RegimentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/regiments")
public class RegimentController {
    @Autowired
    private RegimentService regimentService;

    @Operation(summary = "Create new regiment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Created successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegimentRequest.class))}),
            @ApiResponse(responseCode = "400", description = "Regiment information not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))})
    })
    @PostMapping()
    public ResponseEntity<Void> createRegiment(@Valid @RequestBody RegimentRequest regimentRequest){
        regimentService.createRegiment(regimentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
