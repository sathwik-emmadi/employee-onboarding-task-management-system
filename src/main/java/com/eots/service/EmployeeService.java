package com.eots.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.eots.dto.request.RegisterRequest;
import com.eots.dto.response.EmployeeResponse;
import com.eots.entity.Employee;
import com.eots.exception.EmailAlreadyExistsException;
import com.eots.mapper.EmployeeMapper;
import com.eots.repository.EmployeeRepository;
import com.eots.security.JwtService;
import com.eots.exception.EmailAlreadyExistsException;

import java.util.Optional;
import com.eots.dto.request.LoginRequest;
import com.eots.dto.response.LoginResponse;
import com.eots.exception.InvalidCredentialsException;
import java.util.List;

@Service
public class EmployeeService {

        private final EmployeeRepository employeeRepository;
        private final BCryptPasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        public EmployeeService(EmployeeRepository employeeRepository,
                        BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
                this.employeeRepository = employeeRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtService = jwtService;
        }

        public EmployeeResponse registerEmployee(RegisterRequest request) {

                if (employeeRepository.existsByEmail(request.getEmail())) {
                        throw new EmailAlreadyExistsException("Email already exists.");
                }

                Employee employee = EmployeeMapper.toEntity(request);

                employee.setPassword(
                                passwordEncoder.encode(request.getPassword()));

                Employee savedEmployee = employeeRepository.save(employee);

                return EmployeeMapper.toResponse(savedEmployee);
        }

        public LoginResponse loginEmployee(LoginRequest request) {

                Optional<Employee> optionalEmployee = employeeRepository.findByEmail(
                                request.getEmail());

                if (optionalEmployee.isEmpty()) {
                        throw new InvalidCredentialsException(
                                        "Invalid email or password.");
                }

                Employee employee = optionalEmployee.get();

                if (!passwordEncoder.matches(
                                request.getPassword(),
                                employee.getPassword())) {

                        throw new InvalidCredentialsException(
                                        "Invalid email or password.");
                }

                String token = jwtService.generateToken(
                                employee.getEmail());

                LoginResponse response = new LoginResponse();

                response.setId(employee.getId());

                response.setFullName(
                                employee.getFullName());

                response.setEmail(
                                employee.getEmail());

                response.setRole(
                                employee.getRole());

                response.setToken(token);

                return response;
        }

        public List<EmployeeResponse> getAllEmployees() {

                return employeeRepository.findAll()
                                .stream()
                                .filter(employee -> employee.getRole().name().equals("EMPLOYEE"))
                                .map(EmployeeMapper::toResponse)
                                .toList();
        }
}