package com.lrtech.desafio_padroes_de_projeto.services;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;
import com.lrtech.desafio_padroes_de_projeto.entities.Endereco;
import com.lrtech.desafio_padroes_de_projeto.repositories.ClientRepository;
import com.lrtech.desafio_padroes_de_projeto.repositories.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (!dto.endereco().getCep().matches("\\d{5}-\\d{3}") || !dto.endereco().getCep().matches("\\d{8}")) {
            throw new RuntimeException("cep em formato invalido");
        };

        Cliente cliente = salvarComCep(dto);
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
    public List<Cliente> findAll() {
        return clientRepository.findAll();
    }

    //ajeitar atualizar to criando
    @Override
    public ClienteDTO atualizarCliente(UUID id, ClienteDTO dto) {
        //ajeitar esse repetição do busca endereço
        existeCliente(id);
        Cliente cliente = clientRepository.findById(id).get();
        Endereco endereco = enderecoRepository.findById(dto.endereco().getCep()).orElseGet(() -> {
            Endereco enderecoIntegrado = cepService.consultarEndereco(dto.endereco().getCep());
            enderecoRepository.save(enderecoIntegrado);
            return enderecoIntegrado;
        });
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setEndereco(endereco);
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
            throw new RuntimeException("Usuario não existe na base de dados ");
        }
    }

    private Cliente salvarComCep(ClienteDTO dto) {
        Endereco endereco = enderecoRepository.findById(dto.endereco().getCep()).orElseGet(() -> {
            Endereco enderecoIntegrado = cepService.consultarEndereco(dto.endereco().getCep());
            enderecoRepository.save(enderecoIntegrado);
            return enderecoIntegrado;
        });
        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setId(dto.id());
        clienteSalvo.setNome(dto.nome());
        clienteSalvo.setEmail(dto.email());

        clienteSalvo.setEndereco(endereco);
        return  clienteSalvo;
    }
}
