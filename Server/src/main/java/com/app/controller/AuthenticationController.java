package com.app.controller;

import com.app.DTO.AuthenticateionResponse;
import com.app.DTO.AuthenticationRequest;
import com.app.DTO.RegisterRequest;
import com.app.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Registra um novo usuário no sistema.
     *
     * @param request O objeto de requisição de registro.
     * @return Um {@link ResponseEntity} contendo o token de autenticação.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticateionResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * Autentica um usuário no sistema.
     *
     * @param request O objeto de requisição de autenticação.
     * @return Um {@link ResponseEntity} contendo o token de autenticação.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateionResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
