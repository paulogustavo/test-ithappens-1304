package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.*;
import com.ithappens.estoque.repository.EstoqueRepository;
import com.ithappens.estoque.repository.ItemPedidoRepository;
import com.ithappens.estoque.repository.PedidoEstoqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoEstoqueRepository pedidoEstoqueRepository;
    private final EstoqueRepository estoqueRepository;

    public ItemPedido salvar(ItemPedido itemPedido){
        verificaSeValoresDoItemPedidoJaSeEncontramNoPedido(itemPedido);
        itemPedido = verificaQuantidadeNoEstoque(itemPedido);
        return itemPedidoRepository.save(itemPedido);

    }

    private void verificaSeValoresDoItemPedidoJaSeEncontramNoPedido(ItemPedido itemPedido){
        List<ItemPedido> itemPedidoList = itemPedidoRepository
                .findAllByPedidoEstoqueId(itemPedido.getPedidoEstoque().getId());
        if (itemPedidoList.stream()
                .anyMatch(item -> item.getProduto().getId().equals(itemPedido.getProduto().getId()))
                && !itemPedido.getStatusItemPedido().getId().equals(StatusItemPedido.CANCELADO)) {
            throw new EstoqueCustomException("O produto já encontra-se na lista.");
        }
    }

    private ItemPedido verificaQuantidadeNoEstoque(ItemPedido itemPedido){
        Optional<PedidoEstoque> pedidoEstoqueOptional = pedidoEstoqueRepository
                .findById(itemPedido.getPedidoEstoque().getId());
        if(pedidoEstoqueOptional.isPresent()){
            if(pedidoEstoqueOptional.get().getTipoPedidoEstoque().getId().equals(TipoPedidoEstoque.ENTRADA))
                return itemPedido;
            else {
                //verificar se a quantidade disponível em estoque para o produto e para filial é compatível
                Optional<Estoque> estoqueOptional = estoqueRepository
                        .findByFilialIdAndProdutoId(pedidoEstoqueOptional.get().getFilial().getId(), itemPedido.getProduto().getId());
                if(estoqueOptional.isPresent()){
                    if(estoqueOptional.get().getQuantidade() < itemPedido.getQuantidade()
                            || estoqueOptional.get().getQuantidade().equals(0))
                        throw new EstoqueCustomException("Quantidade em estoque insuficiente.");
                    itemPedido.setValorUnitario(estoqueOptional.get().getProduto().getValorUnitario());
                    return itemPedido;

                } else throw new EstoqueCustomException("Estoque inexistente.");
            }
        } else {
            throw new EstoqueCustomException("Pedido não existe.");
        }

    }


}
