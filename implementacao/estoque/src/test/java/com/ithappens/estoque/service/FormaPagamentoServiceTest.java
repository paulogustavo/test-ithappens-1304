package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.FormaPagamento;
import com.ithappens.estoque.repository.FormaPagamentoRepository;
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
public class FormaPagamentoServiceTest {

    @Mock
    FormaPagamentoRepository formaPagamentoRepository;

    @InjectMocks
    FormaPagamentoService formaPagamentoService;

    private FormaPagamento formaPagamento;

    @BeforeEach
    public void carregaFormaPagamento() {
        formaPagamento = new FormaPagamento();
        formaPagamento.setId(2L);
        formaPagamento.setDescricao("Boleto");
    }

    @Test
    public void deveRetornarFormaDePagamentoSalva_QuandoSalvarFormaDePagamento(){
        formaPagamento.setId(null);
        when(formaPagamentoRepository.save(any(FormaPagamento.class))).thenReturn(formaPagamento);
        when(formaPagamentoRepository.findByDescricao(formaPagamento.getDescricao())).thenReturn(Optional.empty());

        FormaPagamento formaPagamentoSalva = formaPagamentoService.salvar(new FormaPagamento());

        Assertions.assertEquals(formaPagamentoSalva.getId(), formaPagamento.getId());
        Assertions.assertEquals(formaPagamentoSalva.getDescricao().toUpperCase(), formaPagamento.getDescricao().toUpperCase());
    }

    @Test
    public void deveRetornarFilialAtualizada_QuandoAtualizarFilial(){
        FormaPagamento formaPagamentoAAtualizar = new FormaPagamento();
        formaPagamentoAAtualizar.setId(1L);
        formaPagamentoAAtualizar.setDescricao("Eletro Mateus Cidade Operária I");

        when(formaPagamentoRepository.findByDescricao("Eletro Mateus Cidade Operária I")).thenReturn(Optional.empty());
        when(formaPagamentoRepository.save(any(FormaPagamento.class))).thenReturn(formaPagamentoAAtualizar);

        FormaPagamento formaPagamentoAtualizada = formaPagamentoService.atualizar(formaPagamentoAAtualizar);

        Assertions.assertEquals(formaPagamentoAtualizada.getId(), formaPagamentoAAtualizar.getId());
        Assertions.assertEquals(formaPagamentoAtualizada.getDescricao().toUpperCase(), formaPagamentoAAtualizar.getDescricao().toUpperCase());
    }

    @Test
    public void deveLancarExcecao_QuandoTentarSalvarFilialComDescricaoJaExistenteNaBase(){

        FormaPagamento formaPagamentoASalvar = new FormaPagamento();
        formaPagamentoASalvar.setDescricao("Eletro Mateus Cidade Operária");

        when(formaPagamentoRepository.findByDescricao(formaPagamentoASalvar.getDescricao())).thenReturn(Optional.of(formaPagamento));

        Assertions.assertThrows(EstoqueCustomException.class, () -> formaPagamentoService.salvar(formaPagamentoASalvar));
    }

}
