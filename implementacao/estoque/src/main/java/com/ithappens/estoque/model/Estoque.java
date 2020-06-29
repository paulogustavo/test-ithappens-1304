package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "public", name = "estoque")
@SequenceGenerator(
        name = "estoque_id_estoque_seq",
        sequenceName = "estoque_id_estoque_seq",
        allocationSize = 1
)
public class Estoque implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "estoque_id_estoque_seq"
    )
    @Column(name = "id_estoque")
    private Long id;

    @NotNull(message = "Informe o código da filial")
    @ManyToOne
    @JoinColumn(name = "fk_id_filial")
    private Filial filial;

    @NotNull(message = "Informe o código do produto")
    @ManyToOne
    @JoinColumn(name = "fk_id_produto")
    private Produto produto;

    @NotNull(message = "Informe a quantidade")
    @Column(name = "quantidade")
    private Integer quantidade;

}
