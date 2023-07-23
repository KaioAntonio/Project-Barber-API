package com.projectbarber.domain.user.dto;

import lombok.Data;

@Data
public class CreateUserDTO {

    String name;
    String email;
    String password;

}
