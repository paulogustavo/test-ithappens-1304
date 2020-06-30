package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(schema = "public", name = "status_item_pedido")
@SequenceGenerator(
        name = "status_item_pedido_id_status_item_pedido_seq",
        sequenceName = "status_item_pedido_id_status_item_pedido_seq",
        allocationSize = 1
)
public class StatusItemPedido {

    public static long ATIVO = 1L;
    public static long CANCELADO = 2L;
    public static long PROCESSADO = 3L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "status_item_pedido_id_status_item_pedido_seq"
    )
    @Column(name = "id_status_item_pedido")
    private Long id;

    @NotBlank(message = "Descrição do status do item de pedido não pode ser nula")
    @Column(name = "descricao")
    private String descricao;

}
