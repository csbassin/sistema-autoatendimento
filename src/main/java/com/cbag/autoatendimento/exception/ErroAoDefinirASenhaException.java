package com.cbag.autoatendimento.exception;

public class ErroAoDefinirASenhaException extends RuntimeException {
    public ErroAoDefinirASenhaException(String submessage) {
        super("Erro ao definir a senha: "+submessage);
    }
}
