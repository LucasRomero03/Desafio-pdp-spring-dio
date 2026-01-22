package com.lrtech.desafio_padroes_de_projeto.repositories;

import com.lrtech.desafio_padroes_de_projeto.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,String> {
}
