package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.PedidoEstoque;
import com.ithappens.estoque.repository.PedidoEstoqueRepository;
import com.ithappens.estoque.service.PedidoEstoqueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/pedido")
public class PedidoEstoqueController {

    public final PedidoEstoqueService pedidoEstoqueService;
    public final PedidoEstoqueRepository pedidoEstoqueRepository;

    @GetMapping
    public ResponseEntity<List<PedidoEstoque>> buscar() {
        List<PedidoEstoque> pedidoEstoques = pedidoEstoqueRepository.findAll();
        if(pedidoEstoques.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(pedidoEstoques, HttpStatus.OK);
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoEstoque> buscarPorId(@PathVariable Long idPedido) {
        Optional<PedidoEstoque> pedidoEstoqueOptional = Optional.ofNullable(pedidoEstoqueService.buscarPorId(idPedido));
        return pedidoEstoqueOptional.map(pedidoEstoque -> new ResponseEntity<>(pedidoEstoque, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    public ResponseEntity<PedidoEstoque> salvar(@Valid @RequestBody PedidoEstoque pedidoEstoque) {
        PedidoEstoque novo = pedidoEstoqueService.salvar(pedidoEstoque);
        return new ResponseEntity<>(novo, HttpStatus.CREATED);
    }

    @PostMapping(value = "/finalizarPedidoEntrada/{idPedido}")
    public ResponseEntity<PedidoEstoque> finalizarPedidoEntrada(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoEstoqueService.finalizaPedidoEstoque(id, null), HttpStatus.OK);
    }

    @PostMapping(value = "/finalizarPedidoSaida/{idPedido}/{idFormaPagamento}")
    public ResponseEntity<PedidoEstoque> finalizarPedidoSaida(@PathVariable Long id, @PathVariable Long idFormaPagamento) {
        return new ResponseEntity<>(pedidoEstoqueService.finalizaPedidoEstoque(id, idFormaPagamento), HttpStatus.OK);
    }

}
