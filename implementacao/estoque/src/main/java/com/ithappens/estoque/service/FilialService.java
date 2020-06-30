package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.repository.FilialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FilialService {

    private FilialRepository filialRepository;

    public Filial salvar(Filial filial){
        if(filial.getId() != null){
            throw new EstoqueCustomException("Id deve ser nulo ao salvar um novo registro de filial");
        }

        verificaNomeJaExistenteNaBaseAoSalvarNovaFilial(filial);

        return filialRepository.save(filial);

    }

    private void verificaNomeJaExistenteNaBaseAoSalvarNovaFilial(Filial filial){
        Optional<Filial> filialOptional = filialRepository.findByNome(filial.getNome());

        if(filialOptional.isPresent()) throw new EstoqueCustomException("Nome já atribuido a outra filial");
    }

    public Filial atualizar(Filial filial){
        if(filial.getId() == null){
            throw new EstoqueCustomException("Para atualizar é necessário informar o id da filial");
        }

        verificaNomeJaExistenteNaBaseAoAtualizarFilial(filial);

        return filialRepository.save(filial);
    }

    private void verificaNomeJaExistenteNaBaseAoAtualizarFilial(Filial filial){
        Optional<Filial> filialOptional = filialRepository.findByNome(filial.getNome());

        if(filialOptional.isPresent() && !filial.getId().equals(filialOptional.get().getId())){
            throw new EstoqueCustomException("Nome já atribuído a outra filial");
        }
    }

}
