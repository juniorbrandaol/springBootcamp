package com.eblj.catalog.services.exceptions;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException()
    {  super("Token inválido");
    }
}
