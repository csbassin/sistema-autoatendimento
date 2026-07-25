package com.cbag.autoatendimento;

import com.cbag.autoatendimento.enums.TipoSalgado;
import com.cbag.autoatendimento.model.Bebida;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.model.Salgado;
import com.cbag.autoatendimento.repo.MovimentacaoEstoqueRepository;
import com.cbag.autoatendimento.service.BebidaService;
import com.cbag.autoatendimento.service.MovimentacaoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cbag.autoatendimento.service.SalgadoService;

@SpringBootApplication
public class AutoatendimentoApplication implements CommandLineRunner {

    @Autowired
    private SalgadoService salgadoService;
    @Autowired
    private BebidaService bebidaService;
    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    public static void main(String[] args) {
        SpringApplication.run(AutoatendimentoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        //teste
        salgadoService.cadastrar(new Salgado(1, "Coxinha", 10.00, "imagem lol", 50, TipoSalgado.FRITO));
        bebidaService.cadastrar(new Bebida((long)2, "Fanta Laranja", 4.00, "imagem", 0,0));
        Bebida coca = bebidaService.cadastrar(new Bebida((long)3, "Coca-Cola", 5.00, "k", 10,1));
        //imprimir listagem de bebidas
        System.out.println("Bebidas cadastradas: ");
        for(Bebida b:bebidaService.recuperarTudo()){
            System.out.println("    ->"+b.toString());
        }
        System.out.println("Bebidas com estoque maior que zero: ");
        for(Bebida b:bebidaService.recuperarWhereEstoqueMaiorQueZero()){
            System.out.println("    ->"+b.toString());
        }
        // colocando uma alteração de estoque, teste porque temos dados redundantes
        movimentacaoEstoqueService.cadastrar(new MovimentacaoEstoque(coca, -10, "alterada nos testes."));
        //fim teste
    }
}
