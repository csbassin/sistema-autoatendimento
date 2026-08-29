package com.cbag.autoatendimento.exception;

public class SenhaIndefinidaException extends RuntimeException {
    public SenhaIndefinidaException() {
        super("A senha não foi definida anteriormente.");
    }
}
