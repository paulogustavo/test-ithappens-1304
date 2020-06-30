package com.ithappens.estoque.repository;

import com.ithappens.estoque.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("select ip from ItemPedido ip " +
            "join ip.pedidoEstoque pe " +
            "join ip.statusItemPedido sip " +
            "where pe.id = :idPedido " +
            "and sip.id = 1")
    List<ItemPedido> findAllComStatusAtivoPorPedidoId(Long idPedido);

    List<ItemPedido> findAllByPedidoEstoqueId(Long idPedidoEstoque);

}
