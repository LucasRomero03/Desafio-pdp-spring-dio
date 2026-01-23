package com.lrtech.desafio_padroes_de_projeto.services;

import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;
import com.lrtech.desafio_padroes_de_projeto.repositories.ClientRepository;
import com.lrtech.desafio_padroes_de_projeto.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClientRepository  clientRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Cliente findByNome(UUID id) {
        return clientRepository.findById(id).get();

    }

    @Override
    public void deleteCliente(UUID id) {

    }

    @Override
    public void atualizarCliente(UUID id, Cliente cliente) {

    }

    @Override
    public void saveCliente(Cliente cliente) {
        enderecoRepository.save(cliente.getEndereco());
        clientRepository.save(cliente);

    }
}
