package com.lrtech.desafio_padroes_de_projeto.Controllers;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;
import com.lrtech.desafio_padroes_de_projeto.entities.Endereco;
import com.lrtech.desafio_padroes_de_projeto.repositories.ClientRepository;
import com.lrtech.desafio_padroes_de_projeto.services.CepService;
import com.lrtech.desafio_padroes_de_projeto.services.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/client")
public class ClientController {
    @Autowired
    private ClienteServiceImpl clienteService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CepService cepService;

    //TODO colocar paginado
    //TODO colocar xeception handler
    //TODO validation bean "@Valid"
    //TODO controle de acesso?
    //TODO read me
    //CHECK
    //TODO OPEN API doc ajustar
    //TODO aplicar DTO(record)
    //TODO colocar os response entities

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> saveCliente(@RequestBody ClienteDTO dto) {
        ClienteDTO dto1 = clienteService.saveCliente(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto1.id()).toUri();
        return ResponseEntity.created(uri).body(dto1);
        //return ResponseEntity.ok().body(dto);
    }
    @GetMapping("/hello/{cep}")
    public Endereco hello(@PathVariable String cep) {
        return cepService.consultarEndereco(cep);
    }

    //TODO refatorar aqui para trazer os dto usar stream para mapear de um tipo para o outro
    @GetMapping()
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok().body(clientRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable UUID id, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok().body(clienteService.atualizarCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable UUID id) {
        return  ResponseEntity.noContent().build();
    }
}
