package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.*;
import com.cbag.autoatendimento.service.PedidoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//traduzir os erros para erros http para que possamos exibir corretamente nos outros ""terminais""(caixa, e totem)
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> naoEncontrado(NaoEncontradoException e) {
        return resposta(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<Map<String, Object>> senhaIncorreta(SenhaIncorretaException e) {
        return resposta(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler({SenhaIndefinidaException.class, SenhaJaDefinidaException.class, SenhaMuitoLongaException.class, ErroAoDefinirASenhaException.class})
    public ResponseEntity<Map<String, Object>> senhaIndefinida(Exception e){
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({CodigoEmUsoException.class, EmUsoException.class, PedidoJaCanceladoException.class})
    public ResponseEntity<Map<String, Object>> conflito(Exception e) {
        return resposta(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler({ItemConfiguracaoNaoEncontradoException.class})
    public ResponseEntity<Map<String, Object>> configNaoEncontrada(ItemConfiguracaoNaoEncontradoException e) {
        return resposta(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AtendimentoEncerradoException.class)
    public ResponseEntity<Map<String, Object>> atendimentoEncerrado(AtendimentoEncerradoException e) {
        return resposta(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler({EstoqueInvalidoException.class, IllegalArgumentException.class,
            ItensPedidoNaoInicializadaException.class, PedidoService.EstoqueInsuficienteRuntime.class})
    public ResponseEntity<Map<String, Object>> requisicaoInvalida(Exception e) {
        return resposta(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> integridade(DataIntegrityViolationException e) {
        return resposta(HttpStatus.CONFLICT, "Os dados enviados não couberam no banco ou violam uma restrição "
                + "(código de barras repetido, texto grande demais). Detalhe: " + e.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacao(MethodArgumentNotValidException e) {
        List<String> erros = new ArrayList<>();
        for (FieldError erro : e.getBindingResult().getFieldErrors()) {
            erros.add(erro.getDefaultMessage());
        }
        ResponseEntity<Map<String, Object>> resposta = resposta(HttpStatus.BAD_REQUEST, "Dados inválidos.");
        resposta.getBody().put("erros", erros);
        return resposta;
    }

    private ResponseEntity<Map<String, Object>> resposta(HttpStatus status, String mensagem) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", LocalDateTime.now().toString());
        corpo.put("status", status.value());
        corpo.put("mensagem", mensagem);
        return ResponseEntity.status(status).body(corpo);
    }
}
