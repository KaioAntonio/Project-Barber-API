package com.projectbarber.domain.user.dto;

import lombok.Data;

@Data
public class AuthenticationDTO {
    String email;
    String password;
}
