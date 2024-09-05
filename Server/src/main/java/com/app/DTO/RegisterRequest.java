package com.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a requisição de registro.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * Primeiro nome do usuário.
     */
    private String firstName;

    /**
     * Último nome do usuário.
     */
    private String lastName;

    /**
     * E-mail do usuário.
     */
    private String email;

    /**
     * Senha do usuário.
     */
    private String password;
}
