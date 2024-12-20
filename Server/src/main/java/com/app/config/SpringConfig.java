package com.app.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Classe de configuração para o Spring Security.
 * <p>
 * Esta classe define a configuração de segurança para a aplicação,
 * especificando as permissões para diferentes rotas e configurando a
 * autenticação OAuth2.
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SpringConfig {

    /**
     * URL do frontend, injetada a partir das configurações do aplicativo para
     * ser usado de forma dinâmica.
     */
    @Value("${frontend.url}")
    private String frontendUrl;

    /**
     * Configura o filtro de segurança da aplicação.
     * <p>
     * Este método configura a segurança HTTP da aplicação, incluindo a
     * desativação da proteção CSRF, a definição das regras de autorização para
     * diferentes rotas e a configuração do login OAuth2.
     * </p>
     *
     * @param http o objeto HttpSecurity utilizado para configurar a segurança
     * HTTP
     * @return um bean SecurityFilterChain que representa a configuração de
     * segurança
     * @throws Exception se ocorrer algum erro durante a configuração do filtro
     * de segurança
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login/**", "/register", "/api/**").permitAll()
                .requestMatchers("/check-auth").authenticated()
                .requestMatchers("/protected/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error")
                )
                .oauth2Login(oauth2Login -> {
                    oauth2Login
                            .loginPage("/login")
                            .successHandler(new AuthenticationSuccessHandler() {
                                @Override
                                public void onAuthenticationSuccess(
                                    HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    Authentication authentication
                                    ) throws IOException {
                                    response.sendRedirect("/login/google-process");
                                }
                            });
                })
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                )
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/login?expired")
                )
                .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    if (request.getRequestURI().equals("/check-auth")) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("{\"authenticated\": false}");
                    } else {
                        response.sendRedirect(frontendUrl + "login");
                    }
                })
                )
                .build();
    }

    /**
     * Bean para o AuthenticationManager utilizado na autenticação de usuários.
     *
     * @param http o objeto HttpSecurity utilizado para construir o
     * AuthenticationManager
     * @return um bean AuthenticationManager que representa o gerenciador de
     * autenticação
     * @throws Exception se ocorrer algum erro durante a configuração do
     * AuthenticationManager
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }

    /**
     * Bean para o PasswordEncoder utilizado na criptografia de senhas.
     *
     * @return uma instância de BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
