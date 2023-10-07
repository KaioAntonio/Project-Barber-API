package com.projectbarber.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import com.projectbarber.domain.barber.Barber;
import com.projectbarber.domain.barber.BarberRepository;
import com.projectbarber.domain.barber.dto.BarberDTO;
import com.projectbarber.domain.barber.dto.CreateBarberDTO;
import com.projectbarber.domain.user.User;
import com.projectbarber.domain.user.UserRepository;

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
            System.out.println(dados.getIdUser());
            User user = userRepository.getReferenceById(dados.getIdUser());
            System.out.println(user);
            
            // Mapear DTO para entidade
            Barber barber = new Barber(dados, user);
            
            // Salva o barbeiro no banco de dados
            barberRepository.save(barber);
            
            // Mapear entidade para DTO
            BarberDTO barberDTO = new BarberDTO();
            barberDTO.setIdBarbeiro(barber.getIdBarbeiro());
            barberDTO.setActive(barber.getActive());
            barberDTO.setCpf(barber.getCpf());
            barberDTO.setIdUser(barber.getUser().getId());
            
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

    @GetMapping("/{id}")
    public ResponseEntity listarBarbeirosById(@PathVariable Long id) {
        try {
            var barber = barberRepository.findById(id);
            if (barber.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(barber, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity editarBarbeiro(@RequestBody BarberDTO dados, @PathVariable Long id) {
        try {
            var barber = barberRepository.findById(id);
            if (barber.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            barber.get().setActive(dados.getActive());
            barber.get().setCpf(dados.getCpf());
            barber.get().setUser(userRepository.getReferenceById(dados.getIdUser()));
            return new ResponseEntity<>(barber, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
