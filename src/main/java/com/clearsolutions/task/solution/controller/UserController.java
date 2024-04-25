package com.clearsolutions.task.solution.controller;

import com.clearsolutions.task.solution.data.SignupRequest;
import com.clearsolutions.task.solution.model.UserDAO;
import com.clearsolutions.task.solution.service.UserService;
import com.clearsolutions.task.solution.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(UserUris.ROOT)
@RequiredArgsConstructor
@Tag(name = "User Library", description = "Endpoints for managing user")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400",description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "409",description = "Conflict")
    })
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest request) {

        UserDAO user = userService.createUser(request);
        return ResponseUtil.generateResponse("User created successfully", HttpStatus.OK, user);
    }

    @PutMapping
    @Operation(summary = "Update current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> updateUser(@RequestBody SignupRequest request) {
        UserDAO user = userService.updateUser(request);
        return ResponseUtil.generateResponse("User updated successfully", HttpStatus.OK, user);
    }

    @DeleteMapping(UserUris.EMAIL)
    @Operation(summary = "Delete current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseUtil.generateResponse("Successfully deleted", HttpStatus.OK);
    }

    @PostMapping(UserUris.SEARCH)
    @Operation(summary = "Search for users by date of birth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<?> searchUsersByBirthDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        List<UserDAO> users = userService.searchUsersByBirthDateRange(fromDate, toDate);
        return ResponseUtil.generateResponse("All users", HttpStatus.OK, users);
    }
}
