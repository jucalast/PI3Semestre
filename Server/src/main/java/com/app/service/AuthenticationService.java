package com.app.service;

import com.app.DTO.AuthenticateionResponse;
import com.app.DTO.AuthenticationRequest;
import com.app.DTO.RegisterRequest;
import com.app.config.JwtService;
import com.app.model.Role;
import com.app.model.User;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço de autenticação responsável por gerenciar o registro e autenticação de usuários.
 * Utiliza o {@link PasswordEncoder} para codificação de senhas e o {@link JwtService} para gerar tokens JWT.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra um novo usuário no sistema.
     *
     * @param request A solicitação de registro contendo informações do usuário.
     * @return Um {@link AuthenticateionResponse} contendo o token JWT gerado para o novo usuário.
     */
    public AuthenticateionResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticateionResponse.builder().token(jwtToken).build();
    }

    /**
     * Autentica um usuário e gera um token JWT.
     *
     * @param request A solicitação de autenticação contendo email e senha do usuário.
     * @return Um {@link AuthenticateionResponse} contendo o token JWT gerado para o usuário autenticado.
     * @throws org.springframework.security.authentication.BadCredentialsException Se as credenciais fornecidas estiverem incorretas.
     */
    public AuthenticateionResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticateionResponse.builder().token(jwtToken).build();
    }
}
