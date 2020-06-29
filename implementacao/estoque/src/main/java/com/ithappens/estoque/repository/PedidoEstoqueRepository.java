package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.PedidoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoEstoqueRepository extends JpaRepository<PedidoEstoque, Long> {
}
