package com.app.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome do produto não pode ser nulo")
    @Size(max = 100, message = "O nome do produto não pode ter mais de 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo")
    @Column(nullable = false)
    private BigDecimal preco;

    private String imagem;

    @JsonProperty("quantidade_estoque")
    @NotNull(message = "A quantidade em estoque não pode ser nula")
    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidadeEstoque;

    private Integer avaliacao;
}
