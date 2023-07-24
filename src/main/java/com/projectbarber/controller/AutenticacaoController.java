package com.projectbarber.controller;

import com.projectbarber.config.security.DadosTokenJWT;
import com.projectbarber.config.security.TokenService;
import com.projectbarber.domain.user.AuthenticationService;
import com.projectbarber.domain.user.User;
import com.projectbarber.domain.user.dto.AuthenticationDTO;
import com.projectbarber.domain.user.dto.CreateUserDTO;
import com.projectbarber.domain.user.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AuthenticationDTO dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getUserPassword());

            var authentication = manager.authenticate(authenticationToken);

            var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        }
        catch (Exception e){
            return new ResponseEntity<>("Erro ao realizar o login!" + e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UserDTO> cadastrar(@RequestBody @Valid CreateUserDTO dados) {
        return new ResponseEntity<>(authenticationService.cadastrar(dados), HttpStatus.CREATED);
    }

}
