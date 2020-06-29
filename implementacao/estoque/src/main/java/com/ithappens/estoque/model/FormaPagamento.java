package com.ithappens.estoque.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "public", name = "forma_pagamento")
@SequenceGenerator(
        name = "forma_pagamento_id_forma_pagamento_seq",
        sequenceName = "forma_pagamento_id_forma_pagamento_seq",
        allocationSize = 1
)
public class FormaPagamento implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "forma_pagamento_id_forma_pagamento_seq"
    )
    @Column(name = "id_forma_pagamento")
    private Long id;

    @NotBlank(message = "Descrição da forma de pagamento não pode ser nula")
    @Column(name = "descricao")
    private String descricao;

}
