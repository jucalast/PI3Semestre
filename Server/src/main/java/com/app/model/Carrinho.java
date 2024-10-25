package com.app.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonProperty("id_user")
    @JoinColumn(name = "id_user")
    private UserModel userModel;

    @ManyToOne
    @JsonProperty("id_produto")
    @JoinColumn(name = "id_produto", referencedColumnName = "id", nullable = false)
    private Produto produto;

    private int quantidade;
}
