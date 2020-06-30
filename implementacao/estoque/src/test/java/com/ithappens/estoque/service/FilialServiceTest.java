package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.repository.FilialRepository;
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
public class FilialServiceTest {

    @Mock
    FilialRepository filialRepository;

    @InjectMocks
    FilialService filialService;

    private Filial filial;

    @BeforeEach
    public void carregaFilial() {
        filial = new Filial();
        filial.setId(1L);
        filial.setNome("Eletro Mateus Cidade Operária");
    }

    @Test
    public void deveRetornarFilialSalva_QuandoSalvarFilial(){
        filial.setId(null);
        when(filialRepository.save(any(Filial.class))).thenReturn(filial);
        when(filialRepository.findByNome(filial.getNome())).thenReturn(Optional.empty());

        Filial filialSalva = filialService.salvar(new Filial());

        Assertions.assertEquals(filialSalva.getId(), filial.getId());
        Assertions.assertEquals(filialSalva.getNome(), filial.getNome());
    }

    @Test
    public void deveRetornarFilialAtualizada_QuandoAtualizarFilial(){
        Filial filialAAtualizar = new Filial();
        filialAAtualizar.setId(1L);
        filialAAtualizar.setNome("Mix Eletro Cidade Operária I");

        when(filialRepository.findByNome("Eletro Mateus Cidade Operária I")).thenReturn(Optional.empty());
        when(filialRepository.save(any(Filial.class))).thenReturn(filialAAtualizar);

        Filial filialAtualizada = filialService.atualizar(filialAAtualizar);

        Assertions.assertEquals(filialAtualizada.getId(), filialAAtualizar.getId());
        Assertions.assertEquals(filialAtualizada.getNome(), filialAAtualizar.getNome());
    }

    @Test
    public void deveLancarExcecao_QuandoTentarSalvarFilialComDescricaoJaExistenteNaBase(){

        Filial filialASalvar = new Filial();
        filialASalvar.setNome("Eletro Mateus Cidade Operária");

        when(filialRepository.findByNome(filialASalvar.getNome())).thenReturn(Optional.of(filial));

        Assertions.assertThrows(EstoqueCustomException.class, () -> filialService.salvar(filialASalvar));
    }

    @Test
    public void deveLancarExcecao_QuandoTentarSalvarFilialComIdJaPreenchido(){
        filial.setId(null);
        Assertions.assertThrows(EstoqueCustomException.class, () -> filialService.atualizar(filial));
    }

    @Test
    public void deveLancarExcecao_QuandoTentarAtualizarFilialComIdNaoPreenchido(){
        filial.setId(null);
        Assertions.assertThrows(EstoqueCustomException.class, () -> filialService.atualizar(filial));
    }



}
