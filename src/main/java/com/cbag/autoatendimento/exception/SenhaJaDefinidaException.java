package com.cbag.autoatendimento.exception;

public class SenhaJaDefinidaException extends RuntimeException {
    public SenhaJaDefinidaException() {
        super("A senha já foi definida anteriormente.");
    }
}
