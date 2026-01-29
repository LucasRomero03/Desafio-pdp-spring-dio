package com.lrtech.desafio_padroes_de_projeto.Exceptions;

public class UserNotFoundException extends  RuntimeException{

    public  UserNotFoundException(String mensage) {
        super(mensage);
    }
}
