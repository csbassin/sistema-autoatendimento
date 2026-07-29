package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.EstoqueInvalidoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movimentacoes") //...:8080/movimentacoes
public class MovimentacaoEstoqueController {
    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    // POST /movimentacoes::: quantidadeAlterada negativa retira do estoque retornar 400 se zerar abaixo de zero
    // ou se o tipo do produto não controlar estoque
    @PostMapping
    public ResponseEntity<MovimentacaoEstoque> adicionar(@Valid @RequestBody MovimentacaoEstoque movimentacaoEstoque)
            throws NaoEncontradoException, EstoqueInvalidoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacaoEstoqueService.cadastrar(movimentacaoEstoque));
    }
}
