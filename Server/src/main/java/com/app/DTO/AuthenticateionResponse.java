package com.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a resposta de autenticação.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateionResponse {

    private String token;
}
