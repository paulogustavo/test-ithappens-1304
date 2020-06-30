package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
    @Query(value = "select * from forma_pagamento where descricao ilike :descricao", nativeQuery = true)
    Optional<FormaPagamento> findByDescricao(@Param("descricao") String descricao);
}
