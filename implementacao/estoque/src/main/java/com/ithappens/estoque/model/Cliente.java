package com.ithappens.estoque.model;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "public", name = "cliente")
@SequenceGenerator(
        name = "cliente_id_cliente_seq",
        sequenceName = "cliente_id_cliente_seq",
        allocationSize = 1
)
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cliente_id_cliente_seq"
    )
    @Column(name = "id_cliente")
    private Long id;

    @NotBlank(message = "Nome do cliente não pode ser nulo")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "O CPF não pode ser nulo")
    @CPF(message = "Informe um CPF válido")
    @Column(name = "cpf")
    private String cpf;

    @NotBlank(message = "O telefone não pode ser nulo")
    @Column(name = "telefone")
    private String telefone;

    @NotBlank(message = "O endereço não pode ser nulo")
    @Column(name = "endereco")
    private String endereco;

}
