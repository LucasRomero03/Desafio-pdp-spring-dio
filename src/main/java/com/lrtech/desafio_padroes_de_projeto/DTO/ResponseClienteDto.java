package com.lrtech.desafio_padroes_de_projeto.DTO;

import com.lrtech.desafio_padroes_de_projeto.Entities.Endereco;

import java.util.UUID;

public record ResponseClienteDto(UUID id, String nome, String email, Endereco endereco) {
}
