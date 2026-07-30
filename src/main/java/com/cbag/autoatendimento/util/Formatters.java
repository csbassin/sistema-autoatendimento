package com.cbag.autoatendimento.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatters {
    private static DateTimeFormatter formatadorComHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static String getDataHoraFormatada(LocalDateTime dataHora){
        return formatadorComHora.format(dataHora);
    }
}
