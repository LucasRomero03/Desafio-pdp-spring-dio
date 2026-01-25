package com.lrtech.desafio_padroes_de_projeto.DTO;

import com.lrtech.desafio_padroes_de_projeto.entities.Endereco;

import java.util.UUID;

public record ClienteDTO(UUID id, String nome, String email, Endereco endereco) {
}
