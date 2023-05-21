package com.utopia.pmc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.request.NewUserRequest;
import com.utopia.pmc.data.dto.request.UserLoginRequest;
import com.utopia.pmc.data.dto.response.UserLoginResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.ForbiddenException;
import com.utopia.pmc.services.authenticate.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginResponse.class))}),
            @ApiResponse(responseCode = "400", description = "User not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))}),
            @ApiResponse(responseCode = "401", description = "Username or password is incorrect. Please try again", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenException.class))})
    })
    @PostMapping
    public ResponseEntity<UserLoginResponse> createUser(@Valid @RequestBody UserLoginRequest userLoginRequest){
        
        return ResponseEntity.status(HttpStatus.OK).body(
                authenticationService.login(userLoginRequest)
        );
    }
}
