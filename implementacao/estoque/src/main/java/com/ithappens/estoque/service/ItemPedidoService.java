package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.*;
import com.ithappens.estoque.repository.EstoqueRepository;
import com.ithappens.estoque.repository.ItemPedidoRepository;
import com.ithappens.estoque.repository.PedidoEstoqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoEstoqueRepository pedidoEstoqueRepository;
    private final EstoqueRepository estoqueRepository;

    @Transactional
    public ItemPedido salvar(ItemPedido itemPedido){
        verificaSeOPedidoJaFoiFinalizado(itemPedido);
        verificaSeValoresDoItemPedidoJaSeEncontramNoPedido(itemPedido);
        verificaQuantidadeNoEstoque(itemPedido);

        StatusItemPedido statusAtivo = new StatusItemPedido();
        statusAtivo.setId(StatusItemPedido.ATIVO);
        itemPedido.setStatusItemPedido(statusAtivo);

        return itemPedidoRepository.save(itemPedido);

    }

    private void verificaSeOPedidoJaFoiFinalizado(ItemPedido itemPedido){
        if(itemPedido.getPedidoEstoque() != null && itemPedido.getPedidoEstoque().getId() != null){
            Optional<PedidoEstoque> pedidoEstoqueOptional = pedidoEstoqueRepository.findById(itemPedido.getPedidoEstoque().getId());
            if(pedidoEstoqueOptional.isPresent()){
                List<ItemPedido> itemPedidoList = itemPedidoRepository.findAllByPedidoEstoqueId(pedidoEstoqueOptional.get().getId());
                if(!itemPedidoList.isEmpty()
                        && itemPedidoList.stream().anyMatch(item -> item.getStatusItemPedido().getId().equals(StatusItemPedido.PROCESSADO)))
                    throw new EstoqueCustomException("Pedido já processado! Impossível adicionar novos itens");
            } else throw new EstoqueCustomException("Pedido não encontrado");
        } else throw new EstoqueCustomException("Informe o código do pedido");
    }

    private void verificaSeValoresDoItemPedidoJaSeEncontramNoPedido(ItemPedido itemPedido){
        List<ItemPedido> itemPedidoList = itemPedidoRepository
                .findAllByPedidoEstoqueId(itemPedido.getPedidoEstoque().getId());
        if (itemPedidoList.stream()
                .anyMatch(item -> itemPedido.getProduto().getId().equals(item.getProduto().getId())
                && !item.getStatusItemPedido().getId().equals(StatusItemPedido.CANCELADO))) {
            throw new EstoqueCustomException("O produto já encontra-se na lista.");
        }
    }

    private void verificaQuantidadeNoEstoque(ItemPedido itemPedido){
        Optional<PedidoEstoque> pedidoEstoqueOptional = pedidoEstoqueRepository
                .findById(itemPedido.getPedidoEstoque().getId());
        if(pedidoEstoqueOptional.isPresent()){
            if(pedidoEstoqueOptional.get().getTipoPedidoEstoque().getId().equals(TipoPedidoEstoque.SAIDA)) {
                //verificar se a quantidade disponível em estoque para o produto e para filial é compatível
                Optional<Estoque> estoqueOptional = estoqueRepository
                        .findByFilialIdAndProdutoId(pedidoEstoqueOptional.get().getFilial().getId(), itemPedido.getProduto().getId());
                if(estoqueOptional.isPresent()){
                    if(estoqueOptional.get().getQuantidade() < itemPedido.getQuantidade()
                            || estoqueOptional.get().getQuantidade().equals(0))
                        throw new EstoqueCustomException("Quantidade em estoque insuficiente.");
                    itemPedido.setValorUnitario(estoqueOptional.get().getProduto().getValorUnitario());

                } else throw new EstoqueCustomException("Estoque inexistente.");
            }
        } else {
            throw new EstoqueCustomException("Pedido não existe.");
        }

    }


}
