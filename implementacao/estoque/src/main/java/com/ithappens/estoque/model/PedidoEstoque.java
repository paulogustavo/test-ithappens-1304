package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "public", name = "pedido_estoque")
@SequenceGenerator(
        name = "pedido_estoque_id_pedido_estoque_seq",
        sequenceName = "pedido_estoque_id_pedido_estoque_seq",
        allocationSize = 1
)
public class PedidoEstoque implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pedido_estoque_id_pedido_estoque_seq"
    )
    @Column(name = "id_pedido_estoque")
    private Long id;

    @NotNull(message = "Informe o código do tipo de pedido")
    @ManyToOne
    @JoinColumn(name = "fk_id_tipo_pedido_estoque")
    private TipoPedidoEstoque tipoPedidoEstoque;

    @NotNull(message = "Informe o código da filial")
    @ManyToOne
    @JoinColumn(name = "fk_id_filial")
    private Filial filial;

    @NotNull(message = "Informe o código do usuário")
    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;

    @NotNull(message = "Informe o código do cliente")
    @ManyToOne
    @JoinColumn(name = "fk_id_cliente")
    private Cliente cliente;

    @NotNull(message = "Informe o código da forma de pagamento")
    @ManyToOne
    @JoinColumn(name = "fk_id_forma_pagamento")
    private FormaPagamento formaPagamento;

    @Column(name = "quantidade")
    private String observacao;

}
