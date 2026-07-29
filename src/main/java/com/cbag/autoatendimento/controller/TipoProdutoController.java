package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.EmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.TipoProduto;
import com.cbag.autoatendimento.service.TipoProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//itens sao deifnidos aqui
@RestController
@RequestMapping("tipos-produto") //...:8080/tipos-produto
public class TipoProdutoController {
    @Autowired
    private TipoProdutoService tipoProdutoService;

    // GET /tipos-produto :: cada tipo já vem com a lista de campos
    @GetMapping
    public List<TipoProduto> listar() {
        return tipoProdutoService.recuperarTudo();
    }

    // GET /tipos-produto/{id}
    @GetMapping("{id}")
    public TipoProduto recuperar(@PathVariable Long id) throws NaoEncontradoException {
        return tipoProdutoService.recuperarPorId(id);
    }

    // POST /tipos-produto : 201 o nome é único, então repetir dá 409
    @PostMapping
    public ResponseEntity<TipoProduto> adicionar(@Valid @RequestBody TipoProduto tipoProduto) throws CodigoEmUsoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoProdutoService.cadastrar(tipoProduto));
    }

    // PUT /tipos-produto/{id} : a lista de campos enviada substitui a atual
    @PutMapping("{id}")
    public TipoProduto alterar(@PathVariable Long id, @Valid @RequestBody TipoProduto tipoProduto) throws NaoEncontradoException {
        return tipoProdutoService.alterar(id, tipoProduto);
    }

    // DELETE /tipos-produto/{id} : 204, 409 se ainda houver produtos usando o tipo
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) throws NaoEncontradoException, EmUsoException {
        tipoProdutoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
