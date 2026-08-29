package com.cbag.autoatendimento.exception;

public class SenhaMuitoLongaException extends RuntimeException {
    public SenhaMuitoLongaException() {
        super("A senha deve ter no máximo 10 caracteres.");
    }
}
