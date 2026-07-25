package enums;

public enum TipoSalgado {
    FRITO("frito", true), CONGELADO("congelado", false);
    private final String nome;
    private final boolean boolVal;

    TipoSalgado(String nome, boolean boolVal) {
        this.nome = nome;
        this.boolVal = boolVal;
    }

    public String getNome() {
        return nome;
    }
    public boolean getBoolVal() {
        return boolVal;
    }
    public static TipoSalgado fromBoolean(boolean boolVal) {
        if(boolVal){
            return TipoSalgado.FRITO;
        }
        return TipoSalgado.CONGELADO;
    }
}
