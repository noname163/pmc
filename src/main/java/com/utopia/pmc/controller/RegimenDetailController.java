package com.utopia.pmc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.request.regimendetail.EditRegimenDetailRequest;
import com.utopia.pmc.data.dto.request.regimendetail.RegimenDetailRequest;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.exceptions.NotFoundException;
import com.utopia.pmc.services.regimenDetail.RegimenDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/regimen-detail")
public class RegimenDetailController {
    @Autowired
    private RegimenDetailService regimenDetailService;

    @Operation(summary = "Get regimen details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get regimen details successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegimenDetailRequest.class)) }),
            @ApiResponse(responseCode = "404", description = "Regiment information not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)) })
    })
    @GetMapping("/{regimenId}")
    public ResponseEntity<List<RegimenDetailResponse>> getRegimenDetails(@PathVariable Long regimenId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                regimenDetailService.getRegimenDetailResponses(regimenId));
    }

    @Operation(summary = "Get regimen detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get regimen detail successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegimenDetailRequest.class)) }),
            @ApiResponse(responseCode = "404", description = "Regiment information not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)) })
    })
    @GetMapping("/view-detail/{regimenDetailId}")
    public ResponseEntity<RegimenDetailResponse> getRegimenDetail(@PathVariable Long regimenDetailId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                regimenDetailService.getRegimenDetailResponse(regimenDetailId));
    }

    @Operation(summary = "Edit regimen detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edit regimen detail successfull."),
            @ApiResponse(responseCode = "404", description = "Regiment information not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)) })
    })
    @PutMapping()
    public ResponseEntity<Void> editRegimenDetail(@RequestBody EditRegimenDetailRequest editRegimenDetailRequest) {
        regimenDetailService.editRegimenDetail(editRegimenDetailRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
