package com.lrtech.desafio_padroes_de_projeto.DTO;

import com.lrtech.desafio_padroes_de_projeto.Entities.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ClienteDTO(UUID id,
                         @NotBlank(message = "Campo Obrigatório") String nome,
                         String email,
                         String senha,
                         @Valid Endereco endereco) {
}
