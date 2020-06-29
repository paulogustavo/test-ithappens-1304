package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        validarCpfNaoExistenteNaBase(cliente.getCpf());
        return clienteRepository.save(cliente);
    }

    private void validarCpfNaoExistenteNaBase(String cpf) {
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        if(clienteOptional.isPresent()) throw new EstoqueCustomException("CPF já cadastrado.");
    }

    public Cliente atualizar(Cliente cliente){
        if(cliente.getId() == null){
            throw new EstoqueCustomException("Id do cliente não informado");
        }

        validarCpfNaoExistenteParaOutroCliente(cliente);
        return clienteRepository.save(cliente);
    }

    private void validarCpfNaoExistenteParaOutroCliente(Cliente cliente){
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cliente.getCpf());

        if(clienteOptional.isPresent() && !clienteOptional.get().getId().equals(cliente.getId())){
            throw new EstoqueCustomException("CPF já cadastrado para outro cliente");
        }
    }

}
