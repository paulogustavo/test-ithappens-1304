package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.EstoqueCustomException;
import com.ithappens.estoque.model.*;
import com.ithappens.estoque.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PedidoEstoqueService {

    private final PedidoEstoqueRepository pedidoEstoqueRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final FilialRepository filialRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final TipoPedidoEstoqueRepository tipoPedidoEstoqueRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final EstoqueRepository estoqueRepository;

    public PedidoEstoque salvar(PedidoEstoque pedidoEstoque){
        if(pedidoEstoque.getId() != null) throw new EstoqueCustomException("Id do pedido deve ser nulo.");

        return persistePedidoEstoque(pedidoEstoque);
    }

    public PedidoEstoque atualizar(PedidoEstoque pedidoEstoque){
        if(pedidoEstoque.getId() == null) throw new EstoqueCustomException("Id do pedido não deve ser nulo.");
        return persistePedidoEstoque(pedidoEstoque);
    }

    private PedidoEstoque persistePedidoEstoque(PedidoEstoque pedidoEstoque){
        verificaExistenciaValoresInformadosNaBase(pedidoEstoque);
        verificaClienteEObservacao(pedidoEstoque);

        return pedidoEstoqueRepository.save(pedidoEstoque);
    }

    private void verificaExistenciaValoresInformadosNaBase(PedidoEstoque pedidoEstoque){
        if(!usuarioRepository.findById(pedidoEstoque.getUsuario().getId()).isPresent())
            throw new EstoqueCustomException("Usuário informado é inválido");

        if(!filialRepository.findById(pedidoEstoque.getFilial().getId()).isPresent())
            throw new EstoqueCustomException("Filial informada é inválida");

        if(!tipoPedidoEstoqueRepository.findById(pedidoEstoque.getTipoPedidoEstoque().getId()).isPresent())
            throw new EstoqueCustomException("Tipo de pedido informado é inválido");

        verificaFormaPagamento(pedidoEstoque.getFormaPagamento().getId());
    }

    private void verificaClienteEObservacao(PedidoEstoque pedidoEstoque){
        if(pedidoEstoque.getTipoPedidoEstoque().getId().equals(PedidoEstoque.SAIDA)){
            if(pedidoEstoque.getCliente() == null || pedidoEstoque.getCliente().getId() == null)
                throw new EstoqueCustomException("É necessário informar o cliente quando o tipo do pedido for saída");

            if(!clienteRepository.findById(pedidoEstoque.getCliente().getId()).isPresent())
                throw new EstoqueCustomException("Cliente informado é inválido");

            if(StringUtils.isEmpty(pedidoEstoque.getObservacao()))
                throw new EstoqueCustomException("É necessário informar a observação quando o tipo do pedido for saída");
        }

    }

    public PedidoEstoque buscarPorId(Long id){
        if(id == null) throw new EstoqueCustomException("Id do pedido não pode ser nulo");

        Optional<PedidoEstoque> pedidoEstoqueOptional = pedidoEstoqueRepository.findById(id);

        if(!pedidoEstoqueOptional.isPresent())
            throw new EstoqueCustomException("Id do pedido informado é inválido");
        else return pedidoEstoqueOptional.get();
    }

    @Transactional
    public PedidoEstoque finalizaPedidoEstoque(Long idPedido, Long idFormaPagamento){
        verificaFormaPagamento(idFormaPagamento);
        verificaPedido(idPedido);

        Optional<PedidoEstoque> pedidoEstoqueOptional = pedidoEstoqueRepository.findById(idPedido);
        Optional<FormaPagamento> formaPagamentoOptional = formaPagamentoRepository.findById(idFormaPagamento);

        if(pedidoEstoqueOptional.isPresent()){
            if(!formaPagamentoOptional.isPresent()){
                throw new EstoqueCustomException("Forma de pagamento inválida");
            }
            return processarPedidoEstoque(pedidoEstoqueOptional.get());
        } else throw new EstoqueCustomException("Pedido inválido");

    }

    public PedidoEstoque processarPedidoEstoque(PedidoEstoque pedidoEstoque){
        List<ItemPedido> itemPedidoList = itemPedidoRepository.findAllComStatusAtivoPorPedidoId(pedidoEstoque.getId());

        if(!itemPedidoList.isEmpty()) {

            if (pedidoEstoque.getTipoPedidoEstoque().getId().equals(PedidoEstoque.ENTRADA)) {
                itemPedidoList.forEach(item -> {
                    processarEstoqueEntrada(pedidoEstoque.getFilial(), item.getProduto(), item.getQuantidade());
                    salvarItemPedidoProcessado(item);
                });
            } else if(pedidoEstoque.getTipoPedidoEstoque().getId().equals(PedidoEstoque.SAIDA)){
                itemPedidoList.forEach(item -> {
                    processarEstoqueSaida(pedidoEstoque.getFilial(), item.getProduto(), item.getQuantidade());
                    salvarItemPedidoProcessado(item);
                });
            }
            return pedidoEstoque;
        } else {
            throw new EstoqueCustomException("Não existem itens de pedido para serem processados.");
        }


    }

    private void processarEstoqueEntrada(Filial filial, Produto produto, Integer quantidade){

        Optional<Estoque> estoqueOptional = estoqueRepository.findByFilialIdAndProdutoId(filial.getId(), produto.getId());

        estoqueOptional.ifPresent(estoque -> {
            estoque.setQuantidade(estoque.getQuantidade() + quantidade);
            estoqueRepository.save(estoque);
        });

        if(!estoqueOptional.isPresent())
            salvaNovoEstoqueParaProdutoEFilial(filial, produto, quantidade);

    }

    private void salvaNovoEstoqueParaProdutoEFilial(Filial filial, Produto produto, Integer quantidade){
        Estoque estoque = new Estoque();
        estoque.setFilial(filial);
        estoque.setProduto(produto);
        estoque.setQuantidade(quantidade);

        estoqueRepository.save(estoque);
    }

    private void processarEstoqueSaida(Filial filial, Produto produto, Integer quantidade){
        Optional<Estoque> estoqueOptional = estoqueRepository.findByFilialIdAndProdutoId(filial.getId(), produto.getId());

        estoqueOptional.ifPresent(estoque -> {
            if(estoque.getQuantidade() < quantidade){
                throw new EstoqueCustomException("Quantidade insuficiente de " + produto.getDescricao() + " na filial " + filial.getNome());
            } else {
                estoque.setQuantidade(estoque.getQuantidade() - quantidade);
                estoqueRepository.save(estoque);
            }
        });

        if(!estoqueOptional.isPresent())
            throw new EstoqueCustomException("Sem estoque de " + produto.getDescricao() + " na filial " + filial.getNome());
    }

    private void salvarItemPedidoProcessado(ItemPedido itemPedido){
        StatusItemPedido statusProcessado = new StatusItemPedido();
        statusProcessado.setId(StatusItemPedido.PROCESSADO);
        itemPedido.setStatusItemPedido(statusProcessado);
        itemPedidoRepository.save(itemPedido);
    }

    private void verificaFormaPagamento(Long idFormaPagamento){
        if(idFormaPagamento == null) throw new EstoqueCustomException("É necessário informar a forma de pagamento");
        if(!formaPagamentoRepository.findById(idFormaPagamento).isPresent())
            throw new EstoqueCustomException("Forma de pagamento informada é inválida");
    }

    private void verificaPedido(Long idPedido){
        if(idPedido == null) throw new EstoqueCustomException("É necessário informar o código do pedido");
        if(!pedidoEstoqueRepository.findById(idPedido).isPresent())
            throw new EstoqueCustomException("Pedido informado é inválido");
    }


}
