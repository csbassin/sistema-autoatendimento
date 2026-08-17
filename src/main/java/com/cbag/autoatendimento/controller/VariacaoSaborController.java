package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.Produto;
import com.cbag.autoatendimento.model.VariacaoSabor;
import com.cbag.autoatendimento.service.VariacaoSaborService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("variacoes-sabor") //...:8080/variacoes-sabor
public class VariacaoSaborController {
    @Autowired
    private VariacaoSaborService variacaoSaborService;

    @GetMapping
    public List<VariacaoSabor> recuperar(@RequestParam(required = false) Long tipoProdutoId){ // quando o primeiro parêmtro existe, recupera todas as variações de sabor daquele tipo
        if(tipoProdutoId != null){
            return variacaoSaborService.findByTipoProduto(tipoProdutoId);
        }else{
            return variacaoSaborService.recuperarTudo();
        }
    }

    @PostMapping
    public ResponseEntity<VariacaoSabor> adicionar(@Valid @RequestBody VariacaoSabor variacaoSabor)
            throws CodigoEmUsoException, NaoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(variacaoSaborService.cadastrar(variacaoSabor));
    }
}
