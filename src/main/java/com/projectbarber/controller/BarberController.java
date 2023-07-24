package com.projectbarber.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectbarber.domain.barber.Barber;
import com.projectbarber.domain.barber.BarberRepository;
import com.projectbarber.domain.barber.dto.BarberDTO;
import com.projectbarber.domain.barber.dto.CreateBarberDTO;
import com.projectbarber.domain.user.User;
import com.projectbarber.domain.user.UserRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/barber")
public class BarberController {

    private final UserRepository userRepository;
    private final BarberRepository barberRepository;

    public BarberController(UserRepository userRepository, BarberRepository barberRepository) {
        this.userRepository = userRepository;
        this.barberRepository = barberRepository;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<BarberDTO> cadastrarBarbeiro(@RequestBody @Valid CreateBarberDTO dados) {
        try {
            // Verifica se o usuário existe antes de criar o barbeiro
            User user = userRepository.getReferenceById(dados.getIdUser());
            
            // Mapear DTO para entidade
            Barber barber = new Barber(dados, user);
            
            // Salva o barbeiro no banco de dados
            barberRepository.save(barber);
            
            // Mapear entidade para DTO
            BarberDTO barberDTO = new BarberDTO();
            barberDTO.setIdBarbeiro(barber.getIdBarbeiro());
            
            // Retorna a resposta de sucesso com o DTO criado
            return new ResponseEntity<>(barberDTO, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            // Caso o usuário não seja encontrado, retorna uma resposta de erro
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Captura qualquer outra exceção e retorna uma resposta de erro genérica
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity listarBarbeiros() {
        try {
            return new ResponseEntity<>(barberRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
