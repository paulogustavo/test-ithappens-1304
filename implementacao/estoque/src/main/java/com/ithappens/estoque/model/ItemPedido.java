package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(schema = "public", name = "item_pedido")
@SequenceGenerator(
        name = "item_pedido_id_item_pedido_seq",
        sequenceName = "item_pedido_id_item_pedido_seq",
        allocationSize = 1
)
public class ItemPedido implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_pedido_id_item_pedido_seq"
    )
    @Column(name = "id_item_pedido")
    private Long id;

    @NotNull(message = "Informe o código do pedido")
    @ManyToOne
    @JoinColumn(name = "fk_id_pedido_estoque")
    private PedidoEstoque pedidoEstoque;

    @NotNull(message = "Informe o código do produto")
    @ManyToOne
    @JoinColumn(name = "fk_id_produto")
    private Produto produto;

    @NotNull(message = "Informe o código do status do item de pedido")
    @ManyToOne
    @JoinColumn(name = "fk_id_status_item_pedido")
    private StatusItemPedido statusItemPedido;

    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    @Column(name = "quantidade")
    private Integer quantidade;

    @NotNull(message = "Informe o valor unitário do produto")
    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

}
