package com.lrtech.desafio_padroes_de_projeto.Controllers;

import com.lrtech.desafio_padroes_de_projeto.DTO.ClienteDTO;
import com.lrtech.desafio_padroes_de_projeto.DTO.ResponseClienteDto;
import com.lrtech.desafio_padroes_de_projeto.Services.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/client")
public class ClientController {
    @Autowired
    private ClienteServiceImpl clienteService;




    @GetMapping("/{id}")
    public ResponseEntity<ResponseClienteDto> getCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }


    @GetMapping()
    public ResponseEntity<Page<ResponseClienteDto>> getAllClientes(Pageable pageable) {
        return ResponseEntity.ok().body(clienteService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseClienteDto> updateCliente(@PathVariable UUID id, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok().body(clienteService.atualizarCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable UUID id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
