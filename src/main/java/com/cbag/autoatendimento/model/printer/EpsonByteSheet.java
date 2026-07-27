package com.cbag.autoatendimento.model.printer;

import java.util.HashMap;

/**
 * Esta classe armazenará os bytes de controle do padrão EPSON ESC POS
 *
 */
public class EpsonByteSheet {
    private static final HashMap<String, Byte[]> bytes = new HashMap<>();
    static{
        bytes.put("INITIALIZE", new Byte[]{0x1B, 0x40});
        bytes.put("LINE FEED", new Byte[]{0x0A});
        bytes.put("FEED N LINES", new Byte[]{0x1B, 0x64});
        bytes.put("BOLD", new Byte[]{0x1B, 0x45}); // 0 = OFF
        bytes.put("UNDERLINE", new Byte[]{0x1B, 0x2D}); // 0 = OFF, 1 = THIN, 2 = THICK
        bytes.put("JUSTIFICATION", new Byte[]{0x1B, 0x61}); // 0 = LEFT, 1 = CENTER, 2 = RIGHT
        bytes.put("CHAR SIZE", new Byte[]{0x1D, 0x21}); // SEI LÁ
        bytes.put("RESET LINE SPACING", new Byte[]{0x1B, 0x32});
        bytes.put("SET LINE SPACING", new Byte[]{0x1B, 0x33}); // n = quantidade de pontos
        bytes.put("FULL PAPER CUT", new Byte[]{0x1D, 0x56, 0x00});
        bytes.put("PARTIAL PAPER CUT", new Byte[]{0x1D, 0x56, 0x01});
        bytes.put("PARTIAL PAPER CUT WITH FEED", new Byte[]{0x1D, 0x56, 0x42}); // n = quantidade de linhas
        bytes.put("CASH DRAWER PULSE", new Byte[]{0x1B, 0x70}); // recebe argumentos m, t1 e t2 que eu não sei pra quê servem
        bytes.put("SET CODE PAGE", new Byte[]{0x1B, 0x74}); // n = 71 -> windows-1252

    }
    public static Byte[] get(String command){
        return bytes.get(command);
    }
}
