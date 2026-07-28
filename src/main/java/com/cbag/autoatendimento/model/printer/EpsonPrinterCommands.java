package com.cbag.autoatendimento.model.printer;


import com.cbag.autoatendimento.exception.QuantidadeDeArgumentosIncorretaException;

import java.util.Arrays;
import java.util.List;

/**
 * Esta classe armazenará os bytes de controle do padrão EPSON ESC POS
 *
 */
public enum EpsonPrinterCommands {
    INITIALIZE(new Byte[]{0x1B, 0x40}, 0),
    LINE_FEED(new Byte[]{0x0A}, 0),
    FEED_N_LINES(new Byte[]{0x1B, 0x64}, 1),
    BOLD(new Byte[]{0x1B, 0x45}, 1),
    UNDERLINE(new Byte[]{0x1B, 0x2D}, 1),
    JUSTIFICATION(new Byte[]{0x1B, 0x61}, 1),
    CHAR_SIZE(new Byte[]{0x1D, 0x21}, 1),
    RESET_LINE_SPACING(new Byte[]{0x1B, 0x32}, 0),
    SET_LINE_SPACING(new Byte[]{0x1B, 0x33}, 1),
    FULL_PAPER_CUT(new Byte[]{0x1D, 0x56, 0x00}, 0),
    PARTIAL_PAPER_CUT(new Byte[]{0x1D, 0x56, 0x01}, 0),
    PARTIAL_PAPER_CUT_WITH_FEED(new Byte[]{0x1D, 0x56, 0x42}, 1),
    CASH_DRAWER_PULSE(new Byte[]{0x1B, 0x70}, 3),
    SET_CODE_PAGE(new Byte[]{0x1B, 0x74}, 1);

    private final Byte[] commandAsBytes;
    private final int amountOfArgs;

    EpsonPrinterCommands(Byte[] bytes, int amountOfArgs) {
        this.commandAsBytes = bytes;
        this.amountOfArgs = amountOfArgs;
    }
    public static List<Byte> writeStringToBuffer(List<Byte> buffer, String mensagem) {
        byte[] stringAsBytes = StringConverter.toIbm437(mensagem);
        for(byte b : stringAsBytes) {
            buffer.add(b);
        }
        return buffer;
    }
    public static List<Byte> writeCommandToBuffer(List<Byte> buffer, EpsonPrinterCommands command){
        if(command.amountOfArgs != 0){
            throw new QuantidadeDeArgumentosIncorretaException("O comando selecionado recebe argumentos.");
        }
        buffer.addAll(Arrays.asList(command.commandAsBytes));
        return buffer;
    }
    public static List<Byte> writeCommadWithArgsToBuffer(List<Byte> buffer, EpsonPrinterCommands command, byte[] args){
        if(args.length != command.amountOfArgs){
            throw new QuantidadeDeArgumentosIncorretaException("A quantidade de argumentos do comando está incorreta.");
        }
        buffer.addAll(Arrays.asList(command.commandAsBytes));
        for(int i=0; i<command.amountOfArgs; i++){
            buffer.add(args[i]);
        }
        return buffer;
    }
    /*private Byte[] get(EpsonPrinterCommands command){
        return command.commandAsBytes;
    }*/
    /*private static final HashMap<String, Byte[]> bytes = new HashMap<>();
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
    }*/
}
