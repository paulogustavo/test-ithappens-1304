package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FilialRepository extends JpaRepository<Filial, Long> {

    @Query(value = "select * from filial where nome ilike :nome", nativeQuery = true)
    Optional<Filial> findByNome(@Param("nome") String nome);

}
