package com.lrtech.desafio_padroes_de_projeto.repositories;

import com.lrtech.desafio_padroes_de_projeto.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ClientRepository extends JpaRepository<Cliente,UUID>{
}
