package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "public", name = "tipo_pedido_estoque")
@SequenceGenerator(
        name = "tipo_pedido_estoque_id_tipo_pedido_estoque_seq",
        sequenceName = "tipo_pedido_estoque_id_tipo_pedido_estoque_seq",
        allocationSize = 1
)
public class TipoPedidoEstoque implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tipo_pedido_estoque_id_tipo_pedido_estoque_seq"
    )
    @Column(name = "id_tipo_pedido_estoque")
    private Long id;

    @NotBlank(message = "Descrição do tipo de pedido não pode ser nula")
    @Column(name = "descricao")
    private String descricao;

}
