package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "public", name = "produto")
@SequenceGenerator(
        name = "produto_id_produto_seq",
        sequenceName = "produto_id_produto_seq",
        allocationSize = 1
)
public class Produto implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "produto_id_produto_seq"
    )
    @Column(name = "id_produto")
    private Long id;

    @NotBlank(message = "Descrição do produto não pode ser nula")
    @Column(name = "descricao")
    private String descricao;

    @NotBlank(message = "Código de barras não pode ser nulo")
    @Pattern(regexp = "^[0-9]+$", message = "Código de barras deve ser somente números")
    @Size(min = 13, max = 13, message = "O código de barras deve possuir 13 dígitos")
    @Column(name = "codigo_barras")
    private String codigoBarras;

}
