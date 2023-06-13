package com.utopia.pmc.controller.medicine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.request.medicine.MedicineRequest;
import com.utopia.pmc.data.dto.request.medicine.UserMedicineRequest;
import com.utopia.pmc.data.dto.response.medicine.SearchMedicineResponse;
import com.utopia.pmc.data.dto.response.user.UserLoginResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.ForbiddenException;
import com.utopia.pmc.services.medicine.MedicineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @Operation(summary = "Create new medicine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create successfull."),
            @ApiResponse(responseCode = "400", description = "Medicine not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @PostMapping()
    public ResponseEntity<Void> createNewMedicine(@RequestBody MedicineRequest medicineRequest) {
        medicineService.createNewMedicine(medicineRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "User create new medicine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)) }),
            @ApiResponse(responseCode = "400", description = "Medicine not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @PostMapping("/user")
    public ResponseEntity<Long> userCreateNewMedicine(@RequestBody UserMedicineRequest userMedicineRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(
            medicineService.userCreateNewMedicine(userMedicineRequest)
        );
    }

    @Operation(summary = "Search medicine by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search successfull.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SearchMedicineResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Medicine not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @GetMapping("/{name}")
    public ResponseEntity<List<SearchMedicineResponse>> searchMedicineByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(
                medicineService.searchMedicineByName(name));
    
    }

}
