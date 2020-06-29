package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "public", name = "usuario")
@SequenceGenerator(
        name = "usuario_id_usuario_seq",
        sequenceName = "usuario_id_usuario_seq",
        allocationSize = 1
)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usuario_id_usuario_seq"
    )
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank(message = "Nome não pode ser nula")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "Username não pode ser nulo")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Senha não pode ser nula")
    @Column(name = "senha")
    private String senha;


}
