package com.eots.dto.response;

import com.eots.enums.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    private Long id;

    private String fullName;

    private String email;

    private Role role;

    private String token;
}