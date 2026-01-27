package com.lrtech.desafio_padroes_de_projeto.Repositories;

import com.lrtech.desafio_padroes_de_projeto.Entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,String> {
}
