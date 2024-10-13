package com.app.config;

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
 * Classe de configuração para o Spring Security.
 * <p>
 * Esta classe define a configuração de segurança para a aplicação,
 * especificando as permissões para diferentes rotas e configurando
 * a autenticação OAuth2.
 * </p>
 */
@Configuration
public class SpringConfig {

    /**
     * Configura o filtro de segurança da aplicação.
     * <p>
     * Este método configura a segurança HTTP da aplicação, incluindo
     * a desativação da proteção CSRF, a definição das regras de autorização
     * para diferentes rotas e a configuração do login OAuth2.
     * </p>
     *
     * <ul>
     *     <li>CSRF é desabilitado para simplificar a configuração.</li>
     *     <li>Rotas específicas são configuradas para permitir o acesso
     *         sem autenticação ou exigir a role "ADMIN".</li>
     *     <li>Qualquer outra requisição requer autenticação.</li>
     *     <li>Uma página de login personalizada é usada para o login com OAuth2,
     *         e um manipulador de sucesso é configurado para redirecionar o usuário
     *         após a autenticação.</li>
     * </ul>
     *
     * @param http o objeto HttpSecurity utilizado para configurar a segurança HTTP
     * @return um bean SecurityFilterChain que representa a configuração de segurança
     * @throws Exception se ocorrer algum erro durante a configuração do filtro de segurança
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/login", "/api/**").permitAll()
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> {
                    oauth2Login
                            .loginPage("/login")
                            .successHandler(new AuthenticationSuccessHandler() {
                                /**
                                 * Manipula o sucesso da autenticação.
                                 * <p>
                                 * Este método é chamado quando a autenticação é bem-sucedida.
                                 * Ele redireciona o usuário autenticado para a rota "/process-user".
                                 * </p>
                                 *
                                 * @param request o objeto HttpServletRequest associado à requisição
                                 * @param response o objeto HttpServletResponse associado à resposta
                                 * @param authentication o objeto Authentication contendo os dados da autenticação
                                 * @throws IOException se ocorrer um erro de entrada/saída ao redirecionar a resposta
                                 */
                                @Override
                                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                                    response.sendRedirect("/process-user");
                                }
                            });
                })
                .build();
    }
}
