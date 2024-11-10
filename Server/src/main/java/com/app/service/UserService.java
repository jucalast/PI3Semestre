package com.app.service;

import com.app.model.UserModel;
import com.app.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço responsável pela lógica de negócios relacionada à entidade
 * UserModel. Esta classe implementa a interface UserDetailsService para
 * permitir a autenticação de usuários com base no email.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Construtor que injeta o repositório de usuários e o PasswordEncoder.
     *
     * @param userRepository O repositório de usuários.
     * @param passwordEncoder O encoder de senha.
     */
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

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoles());

        logger.info("Authorities carregadas: {}", authority.getAuthority());

        List<GrantedAuthority> authorities = Collections.singletonList(authority);
        logger.info("Autoridades que serão passadas: {}", authorities);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmailId())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    /**
     * Salva um novo usuário no banco de dados se ele não existir.
     *
     * @param name O nome do usuário.
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
     * @param authentication O objeto Authentication que contém as informações
     * do usuário autenticado.
     * @return O objeto UserModel correspondente ao usuário autenticado.
     */
    public UserModel getUserFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmailId(email);
    }

    /**
     * Atualiza as informações de um usuário existente.
     *
     * @param userId O ID do usuário a ser atualizado.
     * @param updatedUser O objeto UserModel com os dados atualizados do
     * usuário.
     * @throws RuntimeException Se o usuário com o ID fornecido não for
     * encontrado.
     */
    public void updateUser(Long userId, UserModel updatedUser) {
        UserModel existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setEmailId(updatedUser.getEmailId());
        existingUser.setRoles(updatedUser.getRoles());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(existingUser);
    }

}
