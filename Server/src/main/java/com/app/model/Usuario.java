package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa um usuário no sistema de e-commerce.
 */
@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    /**
     * O identificador único do usuário.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome do usuário.
     */
    private String nome;

    /**
     * A senha do usuário, usada para autenticação.
     */
    private String senha;

    /**
     * O endereço do usuário.
     */
    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}
