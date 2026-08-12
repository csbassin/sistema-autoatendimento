package com.cbag.autoatendimento.exception;

public class AtendimentoEncerradoException extends RuntimeException {
    public AtendimentoEncerradoException() {
        super("O autoatendimento foi encerrado. Por favor, dirija-se ao caixa.");
    }
}
