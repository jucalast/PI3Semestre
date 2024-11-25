package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a entidade Endereço para a aplicação.
 * Esta classe está mapeada para a tabela 'TB_ADDRESS' no banco de dados.
 *
 * @author Giovanni
 * @version 1.1
 * @since 2024-10-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_ADDRESS")
public class AddressModel {

    /**
     * Identificador único do endereço.
     * Este campo é gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da rua do endereço.
     * Não pode ser nulo.
     */
    @Column(nullable = false)
    private String street;

    /**
     * Número do endereço.
     * Não pode ser nulo.
     */
    @Column(nullable = false)
    private String number;

    /**
     * Bairro do endereço.
     * Não pode ser nulo.
     */
    @Column(nullable = false)
    private String neighborhood;

    /**
     * Cidade do endereço.
     * Não pode ser nulo.
     */
    @Column(nullable = false)
    private String city;

    /**
     * Estado do endereço.
     * Não pode ser nulo.
     */
    @Column(nullable = false)
    private String state;

    /**
     * Código postal do endereço.
     * Não pode ser nulo.
     */
    @Column(nullable = false)
    private String zipCode;

    /**
     * Relação N:N com UserModel.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "addresses") 
    private List<UserModel> users; 
}
