package com.lrtech.desafio_padroes_de_projeto.services;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClienteService {
    public ClienteDTO findById(UUID id);
    public Page<ClienteDTO> findAll(Pageable pageable);
    public void deleteCliente(UUID id);
    public ClienteDTO atualizarCliente(UUID id,ClienteDTO dto);
    public ClienteDTO saveCliente(ClienteDTO dto);
}
