package com.lrtech.desafio_padroes_de_projeto.Controllers;

import com.lrtech.desafio_padroes_de_projeto.DTO.Auth.RequestLoginDto;
import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.DTO.Exception.CustomError;
import com.lrtech.desafio_padroes_de_projeto.DTO.ResponseClienteDto;
import com.lrtech.desafio_padroes_de_projeto.DTO.Auth.ResponseToken;
import com.lrtech.desafio_padroes_de_projeto.Entities.Cliente;
import com.lrtech.desafio_padroes_de_projeto.Services.Auth.TokenService;
import com.lrtech.desafio_padroes_de_projeto.Services.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @PostMapping(value = "/registrar")
    public ResponseEntity<ResponseClienteDto> createUser(@RequestBody ClienteDTO clienteDto) {
        ResponseClienteDto dto = clienteServiceImpl.saveCliente(clienteDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.id())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseToken> login(@RequestBody RequestLoginDto dto) {
        try {
            var userNamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
            var auth = this.authenticationManager.authenticate(userNamePassword);

            var token = tokenService.generateToken((Cliente) auth.getPrincipal());
            return ResponseEntity.ok(new ResponseToken(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ResponseToken("Credencial invalida"));

        }

    }
}
