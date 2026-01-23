package com.lrtech.desafio_padroes_de_projeto.services;

import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;

import java.util.UUID;

public interface ClienteService {
    public Cliente findByNome(UUID id);
    public void deleteCliente(UUID id);
    public void atualizarCliente(UUID id,Cliente cliente);
    public void saveCliente(Cliente cliente);
}
