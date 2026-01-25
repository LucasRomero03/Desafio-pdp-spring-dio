package com.lrtech.desafio_padroes_de_projeto.services;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;

import java.util.List;
import java.util.UUID;

public interface ClienteService {
    public ClienteDTO findById(UUID id);
    public List<Cliente> findAll();
    public void deleteCliente(UUID id);
    public ClienteDTO atualizarCliente(UUID id,ClienteDTO dto);
    public ClienteDTO saveCliente(ClienteDTO dto);
}
