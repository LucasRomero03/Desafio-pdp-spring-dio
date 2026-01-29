package com.lrtech.desafio_padroes_de_projeto.Services;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.DTO.ResponseClienteDto;
import com.lrtech.desafio_padroes_de_projeto.Entities.Cliente;
import com.lrtech.desafio_padroes_de_projeto.Entities.Endereco;
import com.lrtech.desafio_padroes_de_projeto.Entities.Enums.Role;
import com.lrtech.desafio_padroes_de_projeto.Exceptions.UserNotFoundException;
import com.lrtech.desafio_padroes_de_projeto.Repositories.ClientRepository;
import com.lrtech.desafio_padroes_de_projeto.Repositories.EnderecoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService  {
    private final ClientRepository  clientRepository;
    private final EnderecoRepository enderecoRepository;
    private final CepService cepService;
    private final PasswordEncoder passwordEncoder;

    public ClienteServiceImpl(ClientRepository clientRepository,
                              EnderecoRepository enderecoRepository,
                              CepService cepService, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.enderecoRepository = enderecoRepository;
        this.cepService = cepService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseClienteDto saveCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        dtoToCliente(cliente, dto);
        clientRepository.save(cliente);
        return new ResponseClienteDto(cliente.getId(),cliente.getNome(),cliente.getEmail(),cliente.getEndereco());
    }

    @Override
    public Cliente findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ResponseClienteDto findById(UUID id) {
        existeCliente(id);
        Cliente cliente = clientRepository.findById(id).get();
        return new ResponseClienteDto(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getEndereco());
    }

    @Override
    public Page<ResponseClienteDto> findAll(Pageable pageable) {
        Page<Cliente> pageClientes = clientRepository.findAll(pageable);
        return pageClientes.map(x -> new ResponseClienteDto(x.getId(), x.getNome(), x.getEmail(),x.getEndereco()));
    }

    @Override
    public ResponseClienteDto atualizarCliente(UUID id, ClienteDTO dto) {
        existeCliente(id);
        Cliente cliente = clientRepository.findById(id).get();
        dtoToCliente(cliente, dto);
        clientRepository.save(cliente);
        return new ResponseClienteDto(cliente.getId(),cliente.getNome(),cliente.getEmail(),cliente.getEndereco());
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
        entity.setSenha(passwordEncoder.encode(dto.senha()));
        Endereco endereco = resgatarCep(dto.endereco().getCep());
        entity.setEndereco(endereco);
        entity.setRole(Role.USER);
    }


}
