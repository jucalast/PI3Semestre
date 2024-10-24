package com.app.service;

import com.app.model.UserModel;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço responsável pela lógica de negócios relacionada à entidade
 * UserModel. Esta classe implementa a interface UserDetailsService para permitir
 * a autenticação de usuários com base no email.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor que injeta o repositório de usuários e o PasswordEncoder.
     *
     * @param userRepository O repositório de usuários.
     * @param passwordEncoder O encoder de senha.
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Carrega um usuário pelo nome de usuário (email). Esta implementação é
     * utilizada pelo Spring Security para autenticar usuários.
     *
     * @param username O email do usuário a ser carregado.
     * @return Um objeto UserDetails representando o usuário.
     * @throws UsernameNotFoundException Se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmailId(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmailId())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    /**
     * Salva um novo usuário no banco de dados se ele não existir.
     *
     * @param name  O nome do usuário.
     * @param email O e-mail do usuário.
     */
    public void saveUserIfNotExists(String name, String email) {
        UserModel user = userRepository.findByEmailId(email);
        if (user == null) {
            user = new UserModel();
            user.setUserName(name);
            user.setEmailId(email);
            user.setRoles("ROLE_USER");
            userRepository.save(user);
        }
    }

    /**
     * Salva um usuário fornecido com verificação de e-mail e criptografia de
     * senha.
     *
     * @param user O objeto UserModel a ser salvo.
     * @throws RuntimeException Se o e-mail já estiver cadastrado.
     */
    public void registerUser(UserModel user) {
        UserModel existingUser = userRepository.findByEmailId(user.getEmailId());
        if (existingUser == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles("ROLE_USER");
            userRepository.save(user);
        } else {
            throw new RuntimeException("Email já cadastrado.");
        }
    }

    /**
     * Salva um usuário fornecido.
     *
     * @param user O objeto UserModel a ser salvo.
     */
    public void saveUser(UserModel user) {
        userRepository.save(user);
    }

    /**
     * Obtém um usuário a partir da autenticação fornecida.
     *
     * @param authentication O objeto Authentication que contém as informações do usuário autenticado.
     * @return O objeto UserModel correspondente ao usuário autenticado.
     */
    public UserModel getUserFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmailId(email);
    }
}
