package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void carregaCliente() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Marlene");
        cliente.setCpf("36470260004");
        cliente.setEndereco("Av. 05, N 19, Jardim América");
        cliente.setTelefone("9832577418");
    }

    @Test
    public void deveRetornarClienteSalvo_QuandoSalvarCliente(){
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteRepository.findByCpf("36470260004")).thenReturn(Optional.empty());

        Cliente clienteSalvo = clienteService.salvar(new Cliente());

        Assertions.assertEquals(clienteSalvo.getId(), cliente.getId());
        Assertions.assertEquals(clienteSalvo.getCpf(), cliente.getCpf());
        Assertions.assertEquals(clienteSalvo.getNome(), cliente.getNome());
        Assertions.assertEquals(clienteSalvo.getEndereco(), cliente.getEndereco());
        Assertions.assertEquals(clienteSalvo.getTelefone(), cliente.getTelefone());

    }

    @Test
    public void deveLancarExcecao_QuandoAtualizarClienteComCpfJaInseridoNaBase(){

        when(clienteRepository.findByCpf("36470260004")).thenReturn(Optional.of(cliente));

        Cliente clienteAAtualizar = new Cliente();
        clienteAAtualizar.setNome("Paulo Gustavo");
        clienteAAtualizar.setCpf("36470260004");
        clienteAAtualizar.setEndereco("Ponta do Farol");
        clienteAAtualizar.setTelefone("9832574147");

        Assertions.assertThrows(EstoqueCustomException.class, () -> clienteService.atualizar(clienteAAtualizar));
    }

    @Test
    public void deveLancarExcecao_QuandoSalvarClienteComCpfJaInseridoNaBase(){

        Cliente clienteNaBase = new Cliente();
        clienteNaBase.setId(15L);
        clienteNaBase.setNome("Paulo Gustavo");
        clienteNaBase.setCpf("36470260004");
        clienteNaBase.setEndereco("Ponta do Farol");
        clienteNaBase.setTelefone("9832574147");

        when(clienteRepository.findByCpf("36470260004")).thenReturn(Optional.of(clienteNaBase));

        Assertions.assertThrows(EstoqueCustomException.class, () -> clienteService.salvar(cliente));
    }

    @Test
    public void deveRetornarClienteAtualizado() {
        Cliente clienteAAtualizar = new Cliente();
        clienteAAtualizar.setId(1L);
        clienteAAtualizar.setNome("Marlene Sousa Gonçalves");
        clienteAAtualizar.setCpf("36470260004");
        clienteAAtualizar.setEndereco("Av. 05, N 19, Jardim América");
        clienteAAtualizar.setTelefone("9832577418");

        when(clienteRepository.findByCpf("36470260004")).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAAtualizar);

        Cliente clienteAtualizado = clienteService.atualizar(clienteAAtualizar);

        Assertions.assertEquals(clienteAtualizado.getId(), clienteAAtualizar.getId());
        Assertions.assertEquals(clienteAtualizado.getNome(), clienteAAtualizar.getNome());

    }
}
