package com.projectbarber.domain.barber.dto;

import com.projectbarber.domain.user.dto.UserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateBarberDTO extends UserDTO {

    @NotBlank (message = "O campo CPF é obrigatório!")
    @Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}", message = "O CPF deve estar no formato 000.000.000-00")
    private String cpf;

    private Boolean active = true;

    public Long getIdUser() {
        return super.getId();
    }
    
}
