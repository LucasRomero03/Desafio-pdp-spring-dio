package com.lrtech.desafio_padroes_de_projeto.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_Clintes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String email;

    @ManyToOne
    @JoinColumn(name = "endereço_cep")
    private Endereco endereco;



}
