package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "public", name = "filial")
@SequenceGenerator(
        name = "filial_id_filial_seq",
        sequenceName = "filial_id_filial_seq",
        allocationSize = 1
)
public class Filial implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "filial_id_filial_seq"
    )
    @Column(name = "id_filial")
    private Long id;

    @NotBlank(message = "Nome da filial não pode ser nulo")
    @Column(name = "nome")
    private String nome;

}
