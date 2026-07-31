package com.cbag.autoatendimento.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Converter
public class MapaJsonConverter implements AttributeConverter<Map<String, Object>, String> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> mapa) {
        if (mapa == null || mapa.isEmpty()) {
            return "{}";
        }
        try {
            return MAPPER.writeValueAsString(mapa);
        } catch (Exception e) {
            throw new IllegalArgumentException("no foi possivel converter os dados extras para JSON.", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String json) {
        if (json == null || json.isBlank()) {
            return new LinkedHashMap<>();
        }
        try {
            return MAPPER.readValue(json, new TypeReference<LinkedHashMap<String, Object>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("os dados extras salvos não são um JSON valido: " + json, e);
        }
    }
}
