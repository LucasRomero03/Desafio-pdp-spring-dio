package com.lrtech.desafio_padroes_de_projeto.Controllers.Handlers;

import com.lrtech.desafio_padroes_de_projeto.DTO.Exception.CustomError;
import com.lrtech.desafio_padroes_de_projeto.DTO.Exception.ValidationError;
import com.lrtech.desafio_padroes_de_projeto.Exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(UserNotFoundException message, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(),status.value(), message.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException message, HttpServletRequest request){
        //HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError error = new ValidationError(Instant.now(),status.value(), "Dados Inválidos ", request.getRequestURI());

        for (FieldError fieldError : message.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }
}
