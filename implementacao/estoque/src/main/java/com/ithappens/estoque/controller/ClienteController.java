package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.repository.ClienteRepository;
import com.ithappens.estoque.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteRepository clienteRepository;
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> buscar() {
        List<Cliente> clientes = clienteRepository.findAll();
        if(clientes.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long idCliente) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        return clienteOpt.map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@Valid @RequestBody Cliente cliente) {
        Cliente novo = clienteService.salvar(cliente);
        return new ResponseEntity<>(novo, HttpStatus.CREATED);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> atualizar(@Valid @RequestBody Cliente cliente) {
        Cliente novo = clienteService.atualizar(cliente);
        return new ResponseEntity<>(novo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Cliente> deletar(@PathVariable Long idCliente) {
        clienteRepository.deleteById(idCliente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
