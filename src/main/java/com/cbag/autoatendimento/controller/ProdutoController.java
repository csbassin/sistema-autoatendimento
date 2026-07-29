package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.CampoInvalidoException;
import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.EmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.model.Produto;
import com.cbag.autoatendimento.service.MovimentacaoEstoqueService;
import com.cbag.autoatendimento.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produtos") //...:8080/produtos
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    // GET /produtos:: lista tudo; ?tipo=1 filtra por tipo; ?disponiveis=true só o que dá pra vender agora (estoque disponivel)
    @GetMapping
    public List<Produto> listar(@RequestParam(required = false) Long tipo,
                                @RequestParam(required = false, defaultValue = "false") boolean disponiveis) {
        if (tipo != null) {
            return produtoService.recuperarPorTipo(tipo);
        }
        if (disponiveis) {
            return produtoService.recuperarDisponiveis();
        }
        return produtoService.recuperarTudo();
    }

    // GET /produtos/{codigo} : 404 se não existir
    @GetMapping("{codigo}")
    public Produto recuperar(@PathVariable Long codigo) throws NaoEncontradoException {
        return produtoService.recuperarPorCodigo(codigo);
    }

    // GET /produtos/cod-barras/{codBarras} (duh)
    @GetMapping("cod-barras/{codBarras}")
    public Produto recuperarPorCodBarras(@PathVariable Long codBarras) throws NaoEncontradoException {
        return produtoService.recuperarPorCodBarras(codBarras);
    }

    // POST /produtos retorna 201 com o produto criado ouu 409 se o código já existir
    @PostMapping
    public ResponseEntity<Produto> adicionar(@Valid @RequestBody Produto produto)
            throws CodigoEmUsoException, NaoEncontradoException, CampoInvalidoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.cadastrar(produto));
    }

    // PUT /produtos/{codigo} : o estoque enviado é ignorado, ele só muda por movimentação
    @PutMapping("{codigo}")
    public Produto alterar(@PathVariable Long codigo, @Valid @RequestBody Produto produto)
            throws NaoEncontradoException, CampoInvalidoException {
        return produtoService.alterar(codigo, produto);
    }

    // DELETE /produtos/{codigo} :: 204 ou 409 se o produto já estiver em algum pedido
    @DeleteMapping("{codigo}")
    public ResponseEntity<Void> remover(@PathVariable Long codigo) throws NaoEncontradoException, EmUsoException {
        produtoService.remover(codigo);
        return ResponseEntity.noContent().build();
    }

    // GET /produtos/{codigo}/movimentacoes :: histórico de estoque, do mais recente pro mais antigo
    @GetMapping("{codigo}/movimentacoes")
    public List<MovimentacaoEstoque> listarMovimentacoes(@PathVariable Long codigo) throws NaoEncontradoException {
        produtoService.recuperarPorCodigo(codigo);
        return movimentacaoEstoqueService.recuperarPorProduto(codigo);
    }
}
