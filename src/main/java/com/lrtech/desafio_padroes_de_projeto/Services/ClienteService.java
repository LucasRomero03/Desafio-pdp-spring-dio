package com.lrtech.desafio_padroes_de_projeto.Services;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.DTO.ResponseClienteDto;
import com.lrtech.desafio_padroes_de_projeto.Entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClienteService {
     ResponseClienteDto findById(UUID id);
     Page<ResponseClienteDto> findAll(Pageable pageable);
     void deleteCliente(UUID id);
     ResponseClienteDto atualizarCliente(UUID id,ClienteDTO dto);
     ResponseClienteDto saveCliente(ClienteDTO dto);
     Cliente findByEmail(String email);
}
