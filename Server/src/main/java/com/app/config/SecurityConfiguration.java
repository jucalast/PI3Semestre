package com.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração de segurança para a aplicação, utilizando Spring Security.
 *
 * Esta classe define a configuração de segurança para a aplicação, incluindo o gerenciamento de sessões,
 * a configuração do provedor de autenticação e o filtro de autenticação JWT. O acesso às rotas de autenticação
 * é permitido sem autenticação, enquanto outras rotas requerem autenticação.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configura a cadeia de filtros de segurança para a aplicação.
     *
     * Esta configuração desativa a proteção CSRF, define regras de autorização para rotas específicas,
     * configura a política de criação de sessões e adiciona o filtro de autenticação JWT.
     * O filtro JWT é adicionado antes do filtro padrão de autenticação por nome de usuário e senha.
     *
     * @param http A configuração de segurança HTTP.
     * @return O objeto {@link SecurityFilterChain} configurado.
     * @throws Exception Se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa o CSRF usando lambda
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // Define as rotas públicas
                        .anyRequest().authenticated() // Qualquer outra rota requer autenticação
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Define a política de criação de sessão
                )
                .authenticationProvider(authenticationProvider) // Configura o provedor de autenticação
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // add o filtro JWT

        return http.build();
    }
}

