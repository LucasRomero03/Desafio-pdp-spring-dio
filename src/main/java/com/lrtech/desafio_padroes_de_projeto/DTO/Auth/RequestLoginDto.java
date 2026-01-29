package com.lrtech.desafio_padroes_de_projeto.DTO.Auth;

import jakarta.validation.constraints.NotBlank;

public record RequestLoginDto(String nome,
                              @NotBlank(message = "informe seu email") String email,
                              @NotBlank(message = "informe sua senha") String senha) {
}
