package com.lrtech.desafio_padroes_de_projeto.Controllers;

import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;
import com.lrtech.desafio_padroes_de_projeto.repositories.ClientRepository;
import com.lrtech.desafio_padroes_de_projeto.services.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/client")
public class ClientController {
    @Autowired
    private ClienteServiceImpl clienteService;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable UUID id) {
        return clienteService.findByNome(id);
    }

    @PostMapping
    public void saveCliente(@RequestBody Cliente cliente) {
        clienteService.saveCliente(cliente);
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
    @GetMapping("/all")
    public List<Cliente> getAllClientes() {
        return clientRepository.findAll();
    }
}
