package com.app.config;

import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * Configura o serviço de detalhes do usuário usado para autenticação.
     *
     * Este método fornece uma implementação de {@link UserDetailsService} que carrega um usuário
     * com base no nome de usuário fornecido. Ele utiliza o {@link UserRepository} para buscar o
     * usuário no banco de dados e lança uma {@link UsernameNotFoundException} se o usuário não for encontrado.
     *
     * @return Uma instância de {@link UserDetailsService} configurada para buscar usuários por email.
     * @throws UsernameNotFoundException Se o usuário com o nome fornecido não for encontrado.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Configura o provedor de autenticação usado para autenticar usuários.
     *
     * Este método fornece uma implementação de {@link AuthenticationProvider} que utiliza o
     * {@link UserDetailsService} e o {@link PasswordEncoder} para autenticar usuários.
     *
     * @return Uma instância de {@link AuthenticationProvider} configurada para autenticar usuários.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura o gerenciador de autenticação usado para autenticar usuários.
     *
     * Este método fornece uma implementação de {@link AuthenticationManager} que utiliza o
     * {@link AuthenticationConfiguration} para obter o gerenciador de autenticação.
     *
     * @param config A configuração de autenticação.
     * @return Uma instância de {@link AuthenticationManager} configurada para autenticar usuários.
     * @throws Exception Se ocorrer um erro ao obter o gerenciador de autenticação.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura o codificador de senha usado para codificar senhas.
     *
     * Este método fornece uma implementação de {@link PasswordEncoder} que utiliza o algoritmo
     * BCrypt para codificar senhas.
     *
     * @return Uma instância de {@link PasswordEncoder} configurada para codificar senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
