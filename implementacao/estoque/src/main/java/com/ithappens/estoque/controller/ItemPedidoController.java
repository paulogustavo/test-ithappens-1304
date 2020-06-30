package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.ItemPedido;
import com.ithappens.estoque.repository.ItemPedidoRepository;
import com.ithappens.estoque.service.ItemPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/itemPedido")
public class ItemPedidoController {

    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemPedidoService itemPedidoService;

    @PostMapping
    public ResponseEntity salvar(@Valid @RequestBody ItemPedido itemPedido){
        return new ResponseEntity(itemPedidoService.salvar(itemPedido), HttpStatus.CREATED);
    }
}
