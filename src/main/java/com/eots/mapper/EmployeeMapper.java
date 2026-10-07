package com.eots.mapper;

import com.eots.dto.request.RegisterRequest;
import com.eots.dto.response.EmployeeResponse;
import com.eots.entity.Employee;
import com.eots.enums.Role;

public class EmployeeMapper {

    private EmployeeMapper() {
        // Prevent object creation
    }

    public static Employee toEntity(RegisterRequest request) {

        Employee employee = new Employee();

        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());

        // Password will be encoded in the Service layer
        employee.setPassword(request.getPassword());

        employee.setRole(Role.EMPLOYEE);

        return employee;
    }

    public static EmployeeResponse toResponse(Employee employee) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setFullName(employee.getFullName());
        response.setEmail(employee.getEmail());
        response.setRole(employee.getRole());

        if (employee.getDepartment() != null) {
            response.setDepartmentName(employee.getDepartment().getName());
        }

        response.setCreatedAt(employee.getCreatedAt());

        return response;
    }

}