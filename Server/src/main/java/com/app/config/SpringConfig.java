package com.app.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * Classe de configuração de segurança do Spring Security.
 * <p>
 * A anotação @Configuration indica que esta classe é um componente de configuração
 * do Spring, responsável por definir beans e configurar o comportamento da segurança.
 * <p>
 * Esta configuração utiliza o login OAuth2 e define permissões de acesso às rotas.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-05
 */
@Configuration
public class SpringConfig {

    /**
     * Configura o filtro de segurança da aplicação.
     * <p>
     * Define as regras de autorização para requisições HTTP, permitindo o acesso
     * público às rotas "/", "/login" e "/api/**", enquanto todas as outras rotas requerem autenticação.
     * <p>
     * Também configura o login com OAuth2, especificando uma página de login personalizada
     * e um handler de sucesso que redireciona o usuário para "/process-user" após o login bem-sucedido.
     *
     * @param http Instância do HttpSecurity usada para configurar a segurança da aplicação.
     * @return O SecurityFilterChain configurado.
     * @throws Exception Se houver algum erro na configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/", "/login", "/api/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .oauth2Login(oauth2Login -> {
                    oauth2Login
                            .loginPage("/login")
                            .successHandler(new AuthenticationSuccessHandler() {
                                @Override
                                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                    response.sendRedirect("/process-user");
                                }
                            });
                })
                .build();
    }
}