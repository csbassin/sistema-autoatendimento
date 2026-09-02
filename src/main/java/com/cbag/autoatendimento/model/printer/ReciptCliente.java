package com.cbag.autoatendimento.model.printer;

import com.cbag.autoatendimento.config.StaticConfigObjects;
import com.cbag.autoatendimento.exception.ItensPedidoNaoInicializadaException;
import com.cbag.autoatendimento.exception.PedidoSemNumeroException;
import com.cbag.autoatendimento.model.ItemPedido;
import com.cbag.autoatendimento.model.Pedido;
import com.cbag.autoatendimento.model.PedidoAgendado;
import com.cbag.autoatendimento.util.Formatters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    public void montarBuffer(){
        //inicializar a impressora
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.INITIALIZE);
        // setar página para 0, encoding IBM 437
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.SET_CODE_PAGE, new byte[]{0x0});
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.JUSTIFICATION, new byte[]{0x1}); // centraliza o texto
        // texto: Fábrica Mini-Gostosuras: Autoatendimento
        EpsonPrinterCommands.writeStringToBuffer(buffer, "Fábrica Mini-Gostosuras: Autoatendimento");
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.JUSTIFICATION, new byte[]{0x0});

        //line feed
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        EpsonPrinterCommands.writeStringToBuffer(buffer, "Pedido:");
        //bold
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.BOLD, new byte[]{0x1});

        // nome do cliente e número
        EpsonPrinterCommands.writeStringToBuffer(buffer, pedido.getNumero().toString());
        EpsonPrinterCommands.writeStringToBuffer(buffer, "      Data/Hora: "+ Formatters.getDataHoraFormatada(pedido.getTimestamp()));
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        //undelina o nome do cliente
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.UNDERLINE, new byte[]{0x1});
        EpsonPrinterCommands.writeStringToBuffer(buffer, "Cliente: "+pedido.getNomeCliente());
        //desliga bold
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.UNDERLINE, new byte[]{0x0});
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.BOLD, new byte[]{0x0});
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        EpsonPrinterCommands.writeStringToBuffer(buffer, "----------------------------------------------");
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        if(pedido.getItensPedido() != null){
            for(ItemPedido item: pedido.getItensPedido()){
                EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
               // EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.JUSTIFICATION, new byte[]{0x1}); // texto ao centro
                if(!item.getProduto().has("quantidade")){
                    EpsonPrinterCommands.writeStringToBuffer(buffer, "("+item.getQuantidade()+") - "+item.getProduto().toString());
                }else{
                    EpsonPrinterCommands.writeStringToBuffer(buffer, "("+item.getQuantidade()+") - "+item.getProduto().getString("quantidade")+" unidades: "+item.getProduto().toString());

                }
                EpsonPrinterCommands.writeStringToBuffer(buffer, "  R$ "+Formatters.getValueAsMoney(item.getPreco()));
                if(item.getObservacao().length()>0){
                    for(String linha:item.getObservacao().split("\n")){
                        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
                        EpsonPrinterCommands.writeStringToBuffer(buffer, "    -> "+linha);
                    }
                }
            }
        }
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.JUSTIFICATION, new byte[]{0x0});
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        EpsonPrinterCommands.writeStringToBuffer(buffer, "----------------------------------------------");
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);

        try{
            EpsonPrinterCommands.writeStringToBuffer(buffer, "Subtotal: R$ "+Formatters.getValueAsMoney(pedido.getCachedPreco()));
        }catch(ItensPedidoNaoInicializadaException e){
            EpsonPrinterCommands.writeStringToBuffer(buffer, e.getMessage());
        }
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);

        EpsonPrinterCommands.writeStringToBuffer(buffer, "----------------------------------------------");
        EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        EpsonPrinterCommands.writeCommadWithArgsToBuffer(buffer,EpsonPrinterCommands.JUSTIFICATION, new byte[]{0x1}); // texto ao centro
        if(pedido.getPagamentoPendente()){
            EpsonPrinterCommands.writeStringToBuffer(buffer, "O pagamento está pendente e deve ser realizado no momento da retirada.");
            EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
        }
        if(pedido instanceof PedidoAgendado){
            EpsonPrinterCommands.writeStringToBuffer(buffer, "A retirada do pedido está agendada para: ");
            EpsonPrinterCommands.writeCommandToBuffer(buffer, EpsonPrinterCommands.LINE_FEED);
            EpsonPrinterCommands.writeStringToBuffer(buffer, Formatters.getDataHoraFormatada(((PedidoAgendado) pedido).getTimestampRetirada()));
        }
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
            Process print;
            if(!(StaticConfigObjects.os.contains("Windows"))){ // se não for Windows
                print = Runtime.getRuntime().exec("lp -d "+StaticConfigObjects.printerNames.get("TOTEM")+" "+f.getAbsolutePath());
            }else{
                print = Runtime.getRuntime().exec("copy /b "+f.getAbsolutePath()+" \\\\"+StaticConfigObjects.computerName+"\\"+StaticConfigObjects.printerNames.get("TOTEM"));
            }
            // todo pegar a saída do comando depois
            //copy /b file.bin \\COMPUTER\PrinterName -> aparentemente, isso aqui funciona no windows
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buffer.clear();
    }
}
