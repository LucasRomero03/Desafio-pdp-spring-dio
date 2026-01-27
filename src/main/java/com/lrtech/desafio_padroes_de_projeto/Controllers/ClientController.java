package com.lrtech.desafio_padroes_de_projeto.Controllers;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.Repositories.ClientRepository;
import com.lrtech.desafio_padroes_de_projeto.Services.CepService;
import com.lrtech.desafio_padroes_de_projeto.Services.ClienteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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


    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> saveCliente(@Valid @RequestBody ClienteDTO dto) {
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
