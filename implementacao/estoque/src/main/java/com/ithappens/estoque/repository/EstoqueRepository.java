package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByFilialIdAndProdutoId(Long idFilial, Long idProduto);
}
