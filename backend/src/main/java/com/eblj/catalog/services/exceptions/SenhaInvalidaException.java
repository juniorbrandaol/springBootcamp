package com.eblj.catalog.services.exceptions;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(){
        super("Senha inválida.");
    }
}
