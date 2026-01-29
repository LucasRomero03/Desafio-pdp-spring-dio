package com.lrtech.desafio_padroes_de_projeto.Entities.Enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("admin"),
    USER("user");

    private String role;

    Role(String role){
        this.role = role;
    }

}
