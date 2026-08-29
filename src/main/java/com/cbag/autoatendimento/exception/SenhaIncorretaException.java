package com.cbag.autoatendimento.exception;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException() {
        super("A senha está incorreta.");
    }
}
