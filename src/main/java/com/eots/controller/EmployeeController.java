package com.eots.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eots.dto.request.RegisterRequest;
import com.eots.dto.response.ApiResponse;
import com.eots.dto.response.EmployeeResponse;
import com.eots.service.EmployeeService;
import com.eots.dto.request.LoginRequest;
import com.eots.dto.response.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

        private final EmployeeService employeeService;

        public EmployeeController(EmployeeService employeeService) {
                this.employeeService = employeeService;
        }

        @PreAuthorize("hasRole('HR')")
        @PostMapping("/register")
        public ResponseEntity<ApiResponse<EmployeeResponse>> registerEmployee(
                        @Valid @RequestBody RegisterRequest request) {

                EmployeeResponse employeeResponse = employeeService.registerEmployee(request);

                ApiResponse<EmployeeResponse> response = new ApiResponse<>();

                response.setSuccess(true);
                response.setMessage("Employee registered successfully.");
                response.setData(employeeResponse);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @PreAuthorize("hasRole('HR')")
        @GetMapping
        public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {

                List<EmployeeResponse> employees = employeeService.getAllEmployees();

                ApiResponse<List<EmployeeResponse>> response = new ApiResponse<>();

                response.setSuccess(true);
                response.setMessage("Employees fetched successfully.");
                response.setData(employees);

                return ResponseEntity.ok(response);
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<LoginResponse>> loginEmployee(
                        @Valid @RequestBody LoginRequest request) {

                LoginResponse loginResponse = employeeService.loginEmployee(request);

                ApiResponse<LoginResponse> response = new ApiResponse<>();

                response.setSuccess(true);
                response.setMessage("Login successful.");
                response.setData(loginResponse);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/profile")
        public ApiResponse getProfile(Authentication authentication) {

                UserDetails user = (UserDetails) authentication.getPrincipal();

                return new ApiResponse(
                                true,
                                "Welcome " + user.getUsername());
        }
}