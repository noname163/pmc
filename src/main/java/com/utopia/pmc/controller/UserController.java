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
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.services.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Create new basic user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Created successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NewUserRequest.class))}),
            @ApiResponse(responseCode = "400", description = "User not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))})
    })
    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody NewUserRequest newUserRequest){
        userService.createUser(newUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
