package com.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name="Produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Nome não pode ser nulo")
    @Size(max = 100, message = "Nome não pode ter mais que 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Size(max = 500, message = "Descrição não pode ter mais que 500 caracteres")
    @Column(name = "descricao", length = 500)
    private String descricao;

    @NotNull(message = "Preço não pode ser nulo")
    @Positive(message = "Preço deve ser positivo")
    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @Size(max = 255, message = "Imagem não pode ter mais que 255 caracteres")
    @Column(name = "imagem", length = 255)
    private String imagem;

    @NotNull(message = "Quantidade em estoque não pode ser nula")
    @Positive(message = "Quantidade em estoque deve ser positiva")
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantEstoque;

    @Column(name = "avaliacao")
    private Integer avaliacao;


    // Remove os campos temporários
}
