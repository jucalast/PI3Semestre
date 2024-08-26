package com.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa um endereço associado a um usuário, fornecedor ou pedido.
 */
@Entity
@Table(name = "Endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    /**
     * O identificador único do endereço.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rua do endereço.
     */
    private String rua;

    /**
     * Número da residência.
     */
    private String numero;

    /**
     * Complemento do endereço (ex: apartamento, bloco, etc.).
     */
    private String complemento;

    /**
     * Bairro do endereço.
     */
    private String bairro;

    /**
     * Cidade do endereço.
     */
    private String cidade;

    /**
     * Estado do endereço.
     */
    private String estado;

    /**
     * CEP do endereço.
     */
    private String cep;
}
