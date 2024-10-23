package com.app.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_Carrinho")
    private Long id;

    //@OneToOne
    //JoinColumn(name = "Id_Usuario", referencedColumnName ="id", @ForeignKey(name = "fk_carrinho_usuario")
    //private User user;

    /*@ManyToMany
    @JoinTable(
            name = "carrinho_produto",
            joinColumns = @JoinColumn(name = "Id_Carrinho"),
            inverseJoinColumns = @JoinColumn(name = "Id_Produto")
    )
    */

    @Column(name = "preco", precision =10, scale = 2)
    private BigDecimal valorFrete;
}
