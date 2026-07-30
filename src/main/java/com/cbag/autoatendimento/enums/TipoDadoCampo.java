package com.cbag.autoatendimento.enums;

public enum TipoDadoCampo {
    TEXTO("texto") {
        @Override
        public boolean valido(String valor) {
            return true;
        }
    },
    INTEIRO("inteiro") {
        @Override
        public boolean valido(String valor) {
            try {
                Integer.parseInt(valor);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    },
    DECIMAL("decimal") {
        @Override
        public boolean valido(String valor) {
            try {
                Double.parseDouble(valor);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    },
    BOOLEANO("booleano") {
        @Override
        public boolean valido(String valor) {
            return valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false");
        }
    };

    private final String nome;

    TipoDadoCampo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract boolean valido(String valor);
}
