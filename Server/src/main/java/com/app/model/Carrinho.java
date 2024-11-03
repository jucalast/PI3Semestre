package com.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

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

    @JsonIgnore
    @ManyToOne
    @JsonProperty("id_produto")
    @JoinColumn(name = "id_produto", referencedColumnName = "id", nullable = false)
    private Produto produto;

    private int quantidade;

    public Carrinho(Long userId, Long productId) {

    }

    public Carrinho(UserModel userModel, Produto produto, int quantidade) {
        this.userModel = userModel;
        this.produto = produto;
        this.quantidade = quantidade;
    }
}
