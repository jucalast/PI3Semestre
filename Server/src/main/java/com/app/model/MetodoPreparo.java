package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @NotNull(message = "Nome não pode ser nulo")
    @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres")
    @Column(name = "descricao", length = 255)
    private String descricao;

    @NotNull(message = "Tipo de preparo não pode ser nulo")
    @Size(max = 50, message = "O tipo de preparo não pode ter mais de 50 caracteres")
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

    // Enum para representar a complexidade
    public enum Complexidade {
        FACIL,
        INTERMEDIARIO,
        AVANCADO
    }

    @OneToOne(mappedBy = "metodoPreparo", fetch = FetchType.LAZY)
@JsonBackReference
private Produto produto;

}
