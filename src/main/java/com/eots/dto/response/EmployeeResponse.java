package com.eots.dto.response;

import java.time.LocalDateTime;

import com.eots.enums.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeResponse {

    private Long id;

    private String fullName;

    private String email;

    private Role role;

    private String departmentName;

    private LocalDateTime createdAt;

}