package com.lrtech.desafio_padroes_de_projeto.Controllers;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;
import com.lrtech.desafio_padroes_de_projeto.entities.Endereco;
import com.lrtech.desafio_padroes_de_projeto.repositories.ClientRepository;
import com.lrtech.desafio_padroes_de_projeto.services.CepService;
import com.lrtech.desafio_padroes_de_projeto.services.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //TODO colocar xeception handler
    //TODO validation bean "@Valid"
    //TODO controle de acesso?

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

    @GetMapping()
    public ResponseEntity<Page<ClienteDTO>> getAllClientes(Pageable pageable) {
        return ResponseEntity.ok().body(clienteService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable UUID id, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok().body(clienteService.atualizarCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable UUID id) {
        clienteService.deleteCliente(id);
        return  ResponseEntity.noContent().build();
    }
}
