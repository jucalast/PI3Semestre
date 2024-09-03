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

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
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

    public AuthenticateionResponse authenticate(AuthenticationRequest request) {

        // Logs de entrada
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: " + request.getPassword());

        // Verifica se o usuário foi encontrado
        var userOptional = userRepository.findByEmail(request.getEmail());
        System.out.println("Founded: " + userOptional.isPresent());

        // Loga informações adicionais sobre o usuário, se encontrado
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Stored Password: " + user.getPassword());
            System.out.println("User Account Non Expired: " + user.isAccountNonExpired());
            System.out.println("User Account Non Locked: " + user.isAccountNonLocked());
            System.out.println("User Credentials Non Expired: " + user.isCredentialsNonExpired());
            System.out.println("User Enabled: " + user.isEnabled());
        }

        try {
            // Tenta autenticar o usuário
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            System.out.println("Authenticated");
        } catch (Exception e) {
            // Loga a exceção e retorna uma mensagem amigável
            System.out.println("Authentication failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            throw e; // Re-throw para permitir que o Spring trate a exceção
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticateionResponse.builder().token(jwtToken).build();
    }
}
