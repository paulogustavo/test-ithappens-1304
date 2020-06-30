package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.FormaPagamento;
import com.ithappens.estoque.repository.FormaPagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento salvar(FormaPagamento formaPagamento){
        if(formaPagamento.getId() != null){
            throw new EstoqueCustomException("Id deve ser nulo ao salvar um novo registro de forma de pagamento");
        }

        verificaDescricaoJaExistenteNaBaseAoSalvarNovaFormaDePagamento(formaPagamento);

        return formaPagamentoRepository.save(formaPagamento);

    }

    private void verificaDescricaoJaExistenteNaBaseAoSalvarNovaFormaDePagamento(FormaPagamento formaPagamento){
        Optional<FormaPagamento> formaPagamentoOptional = formaPagamentoRepository.findByDescricao(formaPagamento.getDescricao());

        if(formaPagamentoOptional.isPresent()) throw new EstoqueCustomException("Descrição já atribuida a outra forma de pagamento");
    }

    public FormaPagamento atualizar(FormaPagamento formaPagamento){
        if(formaPagamento.getId() == null){
            throw new EstoqueCustomException("Para atualizar é necessário informar o id da forma de pagamento");
        }

        verificaDescricaoJaExistenteNaBaseAoAtualizarFormaPagamento(formaPagamento);

        return formaPagamentoRepository.save(formaPagamento);
    }

    private void verificaDescricaoJaExistenteNaBaseAoAtualizarFormaPagamento(FormaPagamento formaPagamento){
        Optional<FormaPagamento> formaPagamentoOptional = formaPagamentoRepository.findByDescricao(formaPagamento.getDescricao());

        if(formaPagamentoOptional.isPresent() && !formaPagamento.getId().equals(formaPagamentoOptional.get().getId())){
            throw new EstoqueCustomException("Descrição já atribuída a outra forma de pagamento");
        }
    }

}
