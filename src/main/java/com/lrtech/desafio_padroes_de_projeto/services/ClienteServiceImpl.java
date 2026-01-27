package com.lrtech.desafio_padroes_de_projeto.services;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.Exceptions.UserNotFoundException;
import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;
import com.lrtech.desafio_padroes_de_projeto.entities.Endereco;
import com.lrtech.desafio_padroes_de_projeto.repositories.ClientRepository;
import com.lrtech.desafio_padroes_de_projeto.repositories.EnderecoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClientRepository  clientRepository;
    private final EnderecoRepository enderecoRepository;
    private final CepService cepService;

    public ClienteServiceImpl(ClientRepository clientRepository,
                              EnderecoRepository enderecoRepository,
                              CepService cepService) {
        this.clientRepository = clientRepository;
        this.enderecoRepository = enderecoRepository;
        this.cepService = cepService;
    }
    //TODO ajeitar exeções

    @Override
    public ClienteDTO saveCliente(ClienteDTO dto) {
        if (!dto.endereco().getCep().matches("\\d{5}-\\d{3}")) {
            throw new RuntimeException("cep em formato invalido");
        };
        Cliente cliente = new Cliente();
        dtoToCliente(cliente, dto);
        clientRepository.save(cliente);
        return dto;
    }
    @Override
    public ClienteDTO findById(UUID id) {
        existeCliente(id);
        Cliente cliente = clientRepository.findById(id).get();
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getEndereco());
    }

    @Override
    public Page<ClienteDTO> findAll(Pageable pageable) {
        Page<Cliente> pageClientes = clientRepository.findAll(pageable);
        return pageClientes.map(x -> new ClienteDTO(x.getId(), x.getNome(), x.getEmail(), x.getEndereco()));//clientRepository.findAll();
    }

    @Override
    public ClienteDTO atualizarCliente(UUID id, ClienteDTO dto) {
        //ajeitar esse repetição do busca endereço
        existeCliente(id);
        Cliente cliente = clientRepository.findById(id).get();
        dtoToCliente(cliente, dto);
        clientRepository.save(cliente);
        return dto;
    }

    @Override
    public void deleteCliente(UUID id) {
        existeCliente(id);
        clientRepository.deleteById(id);
    }

    private void existeCliente(UUID id) {
        if(clientRepository.findById(id).isEmpty()){
            throw new UserNotFoundException("Usuario não existe na base de dados ");
        }
    }

    private Endereco resgatarCep(String cep) {
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco enderecoIntegrado = cepService.consultarEndereco(cep);
            enderecoRepository.save(enderecoIntegrado);
            return enderecoIntegrado;
        });

        return  endereco;
    }

    private void dtoToCliente(Cliente entity, ClienteDTO dto) {
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        Endereco endereco = resgatarCep(dto.endereco().getCep());
        entity.setEndereco(endereco);
    }
}
