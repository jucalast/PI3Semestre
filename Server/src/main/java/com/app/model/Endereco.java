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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rua onde o endereço está localizado.
     */
    private String rua;

    /**
     * Número do endereço.
     */
    private String numero;

    /**
     * Complemento do endereço.
     */
    private String complemento;

    /**
     * Bairro onde o endereço está localizado.
     */
    private String bairro;

    /**
     * Cidade onde o endereço está localizado.
     */
    private String cidade;

    /**
     * Estado onde o endereço está localizado.
     */
    private String estado;

    /**
     * CEP do endereço.
     */
    private String cep;
}
