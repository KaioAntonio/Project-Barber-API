package com.projectbarber.controller;

import com.projectbarber.config.exception.CustomException;
import com.projectbarber.config.security.DadosTokenJWT;
import com.projectbarber.config.security.TokenService;
import com.projectbarber.domain.user.AuthenticationService;
import com.projectbarber.domain.user.User;
import com.projectbarber.domain.user.dto.AuthenticationDTO;
import com.projectbarber.domain.user.dto.CreateUserDTO;
import com.projectbarber.domain.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Login do Usuário", description = "Login do Usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Login do Usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__email__**: Email do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__userPassword__**: Senha do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</ul>"
    )
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AuthenticationDTO dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getUserPassword());

            var authentication = manager.authenticate(authenticationToken);

            var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        }
        catch (Exception e){
            throw  new CustomException("Erro ao efetuar o login!");
        }
    }

    @Operation(summary = "Cadastrar do Usuário", description = "Cadastrar do Usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cadastrar do Usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__nome   '__**: Nome do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__email__**: Email do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__userPassword__**: Senha do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</ul>"
    )
    @PostMapping("/cadastrar")
    public ResponseEntity<UserDTO> cadastrar(@RequestBody @Valid CreateUserDTO dados) {
        return new ResponseEntity<>(authenticationService.cadastrar(dados), HttpStatus.CREATED);
    }

}
