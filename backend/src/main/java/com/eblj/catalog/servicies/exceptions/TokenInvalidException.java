package com.eblj.catalog.servicies.exceptions;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException()
    {  super("Token inválido");
    }
}
