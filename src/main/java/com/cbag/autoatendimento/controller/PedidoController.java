package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.dto.PedidoRequest;
import com.cbag.autoatendimento.enums.EstadoPedido;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.exception.PedidoJaCanceladoException;
import com.cbag.autoatendimento.model.Pedido;
import com.cbag.autoatendimento.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // GET /pedidos :: historico completo; ?estado=PREPARANDO filtra pelo estado
    @GetMapping
    public List<Pedido> recuperarTodos(@RequestParam(required = false) EstadoPedido estado) {
        return estado == null ? pedidoService.recuperarTodos() : pedidoService.recuperarPorEstado(estado);
    }

    // GET /pedidos/{numero} :: 404 se nao existir
    @GetMapping("{numero}")
    public Pedido recuperar(@PathVariable Long numero) throws NaoEncontradoException {
        return pedidoService.recuperarPorNumero(numero);
    }

    // POST /pedidos :: confirma o pedido, baixa o estoque e imprime a nota
    @PostMapping
    public ResponseEntity<Pedido> confirmar(@Valid @RequestBody PedidoRequest request) throws NaoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.cadastrarDeRequest(request));
    }

    // PUT /pedidos/{numero}/estado?estado=AGUARDANDO_RETIRADA
    @PutMapping("{numero}/estado")
    public Pedido alterarEstado(@PathVariable Long numero, @RequestParam EstadoPedido estado)
            throws NaoEncontradoException {
        return pedidoService.alterarEstado(numero, estado);
    }

    // PUT /pedidos/{numero}/pagamento :: marca o pagamento como quitado
    @PutMapping("{numero}/pagamento")
    public Pedido confirmarPagamento(@PathVariable Long numero) throws NaoEncontradoException {
        return pedidoService.confirmarPagamento(numero);
    }

    // PUT /pedidos/{numero}/cancelar :: cancela e devolve o estoque dos itens
    @PutMapping("{numero}/cancelar")
    public Pedido cancelar(@PathVariable Long numero) throws NaoEncontradoException, PedidoJaCanceladoException {
        return pedidoService.cancelar(numero);
    }

    // POST /pedidos/{numero}/reimprimir :: manda a nota pra impressora de novo
    @PostMapping("{numero}/reimprimir")
    public Pedido reimprimir(@PathVariable Long numero) throws NaoEncontradoException {
        return pedidoService.reimprimir(numero);
    }

    // GET /pedidos/estados :: lista os estados possiveis pro front montar o seletor
    @GetMapping("estados")
    public EstadoPedido[] estados() {
        return EstadoPedido.values();
    }
}
