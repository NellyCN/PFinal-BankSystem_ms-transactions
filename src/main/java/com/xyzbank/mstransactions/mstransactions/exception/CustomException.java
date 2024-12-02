package com.xyzbank.mstransactions.mstransactions.exception;

import lombok.Getter;

//Excepción personalizada para manejar errores específicos del negocio.

@Getter
public class CustomException extends RuntimeException{
    private final String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }
}
