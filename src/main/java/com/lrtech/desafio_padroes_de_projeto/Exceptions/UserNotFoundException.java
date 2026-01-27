package com.lrtech.desafio_padroes_de_projeto.Exceptions;

import java.util.UUID;

public class UserNotFoundException extends  RuntimeException{

    public  UserNotFoundException(String mensage) {
        super(mensage);
    }
}
