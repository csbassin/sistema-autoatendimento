package com.cbag.autoatendimento.model.printer;

import com.cbag.autoatendimento.config.StaticConfigObjects;
import com.cbag.autoatendimento.exception.PedidoSemNumeroException;
import com.cbag.autoatendimento.model.Pedido;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReciptCliente {
    private Pedido pedido;
    private List<Byte> buffer = new ArrayList<>(81920); // porque o buffer da impressora tem 80k bytes

    public ReciptCliente(Pedido pedido) {
        if(pedido.getNumero() == null){
            throw new PedidoSemNumeroException("Só pode ser imprimida a nota depois que o pedido for salvo no banco de dados!");
        }
        this.pedido = pedido;
    }
   /* private void addTextToBuffer(String text){
        text = new String(text.getBytes(), StandardCharsets.ISO_8859_1); // iso 8859-1 é o mais parecido com ibm 850, que a impressora suporta
        for(int i = 0; i < text.getBytes().length; i++){
            buffer.add(text.getBytes()[i]);
        }
    }
    private void addCommandToBuffer(String command){
        Byte[] bytes;
        bytes = EpsonPrinterCommands.get(command);
        buffer.addAll(Arrays.asList(bytes));
    }
    private void addCommandWithArgumentsToBuffer(String command, byte[] args){
        Byte[] bytes;
        bytes = EpsonPrinterCommands.get(command);
        buffer.addAll(Arrays.asList(bytes));
        for (byte arg : args) {
            buffer.add(arg);
        }
    }*/
    public void montarBuffer(){
        //inicializar a impressora
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.INITIALIZE);
        // setar página para 0, encoding IBM 437
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.SET_CODE_PAGE, new byte[]{0x0});
        // texto: Fábrica Mini-Gostosuras: Autoatendimento
        EpsonPrinterCommands.writeStringToBuffer(buffer, "Fábrica Mini-Gostosuras: Autoatendimento");
        //line feed
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        EpsonPrinterCommands.writeStringToBuffer(buffer, "Pedido:");
        //bold
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.BOLD, new byte[]{0x1});
        // nome do cliente e número
        EpsonPrinterCommands.writeStringToBuffer(buffer, pedido.getNumero().toString());
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        EpsonPrinterCommands.writeStringToBuffer(buffer, pedido.getNomeCliente());
        //desliga bold
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.BOLD, new byte[]{0x0});
        //todo terminar os comandos aqui
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer, EpsonPrinterCommands.PARTIAL_PAPER_CUT_WITH_FEED, new byte[]{0x11});
    }

    public void flushAndPrint() throws FileNotFoundException {
        File f = new File(StaticConfigObjects.userHomeDir + "/notasCliente/");
        if(!f.exists()){
            f.mkdir();
        }
        f =  new File(StaticConfigObjects.userHomeDir + "/notasCliente/"+pedido.getNumero()+".bin");

        byte[] bufferAsArray = new byte[buffer.size()];
        for(int i = 0; i < buffer.size(); i++) {
            bufferAsArray[i] = buffer.get(i);
        }

        try{
            // gravando o arquivo com os comandos de impressora
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bufferAsArray);
            fos.close();
            //rodando o lp -d [printer name] [filepath]
            Process lp = Runtime.getRuntime().exec("lp -d "+StaticConfigObjects.printerNames.get("TOTEM")+" "+f.getAbsolutePath());
            // todo pegar a saída do comando depois
            // todo tentar adicionar suporte à windows
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buffer.clear();
    }
}
