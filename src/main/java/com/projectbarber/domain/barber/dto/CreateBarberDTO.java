package com.projectbarber.domain.barber.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBarberDTO{

    @NotBlank (message = "O campo CPF é obrigatório!")
    String cpf;

    @NotNull (message = "O campo id usuario é obrigatório!")
    Long idUser;

    Short active = 1;

    
    
}
