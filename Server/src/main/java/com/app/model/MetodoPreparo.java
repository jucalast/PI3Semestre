package com.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um método de preparo de café, como Prensa Francesa ou Hario V60.
 * Cada método possui um nome, uma descrição, o tipo de preparo, material,
 * acessórios, complexidade e marca.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metodo_preparo")
public class MetodoPreparo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Tipo de preparo não pode ser nulo")
    @Size(max = 50, message = "O tipo de preparo não pode ter mais de 50 caracteres")
    @JsonProperty("tipo_preparo")
    @Column(name = "tipo_preparo", nullable = false, length = 50)
    private String tipoPreparo;

    @Size(max = 50, message = "O material não pode ter mais de 50 caracteres")
    @Column(name = "material", length = 50)
    private String material;

    @Size(max = 100, message = "Os acessórios não podem ter mais de 100 caracteres")
    @Column(name = "acessorios", length = 100)
    private String acessorios;

    @Enumerated(EnumType.STRING)
    @Column(name = "complexidade")
    private Complexidade complexidade;

    @Size(max = 50, message = "A marca não pode ter mais de 50 caracteres")
    @Column(name = "marca", length = 50)
    private String marca;

    @OneToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Enum para representar a complexidade
    public enum Complexidade {
        FACIL,
        INTERMEDIARIO,
        AVANCADO
    }
}