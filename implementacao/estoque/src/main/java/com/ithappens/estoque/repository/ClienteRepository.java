package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
