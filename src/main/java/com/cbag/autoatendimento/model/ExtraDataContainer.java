package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.model.converter.MapaJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;

import java.util.LinkedHashMap;
import java.util.Map;

//rctra data pra tabela em vez de tabela prorpia
@MappedSuperclass
public abstract class ExtraDataContainer<T extends ExtraDataContainer<T>> {
    @Convert(converter = MapaJsonConverter.class)
    @Column(name = "extra_data", columnDefinition = "TEXT")
    private Map<String, Object> extraData = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public T set(String chave, Object valor) { //ai nao precisa de set pra cada tipo
        if (valor == null) {
            extraData.remove(chave);
        } else {
            extraData.put(chave, valor);
        }
        return (T) this;
    }

    public boolean has(String chave) {
        return extraData.get(chave) != null;
    }

    public void remove(String chave) {
        extraData.remove(chave);
    }

    public String getString(String chave) {
        return getString(chave, null);
    }

    public String getString(String chave, String padrao) {
        Object valor = extraData.get(chave);
        return valor == null ? padrao : valor.toString();
    }

    public int getInt(String chave) {
        return getInt(chave, 0);
    }

    public int getInt(String chave, int padrao) {
        Object valor = extraData.get(chave);
        if (valor instanceof Number numero) {
            return numero.intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(valor).trim());
        } catch (NumberFormatException e) {
            return padrao;
        }
    }

    public long getLong(String chave) {
        return getLong(chave, 0L);
    }

    public long getLong(String chave, long padrao) {
        Object valor = extraData.get(chave);
        if (valor instanceof Number numero) {
            return numero.longValue();
        }
        try {
            return Long.parseLong(String.valueOf(valor).trim());
        } catch (NumberFormatException e) {
            return padrao;
        }
    }

    public double getDouble(String chave) {
        return getDouble(chave, 0.0);
    }

    public double getDouble(String chave, double padrao) {
        Object valor = extraData.get(chave);
        if (valor instanceof Number numero) {
            return numero.doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(valor).trim());
        } catch (NumberFormatException e) {
            return padrao;
        }
    }

    public boolean getBool(String chave) {
        return getBool(chave, false);
    }

    public boolean getBool(String chave, boolean padrao) {
        Object valor = extraData.get(chave);
        if (valor instanceof Boolean booleano) {
            return booleano;
        }
        if (valor == null) {
            return padrao;
        }
        String texto = valor.toString().trim();
        if (texto.equalsIgnoreCase("true")) {
            return true;
        }
        if (texto.equalsIgnoreCase("false")) {
            return false;
        }
        return padrao;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData == null ? new LinkedHashMap<>() : new LinkedHashMap<>(extraData);
    }
}
