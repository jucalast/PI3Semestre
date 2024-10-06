package com.app.service;

import com.app.model.UserModel;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço responsável pela lógica de negócios relacionada à entidade UserModel.
 *
 * A anotação @Service indica que esta classe é um componente de serviço do Spring,
 * gerenciado pelo container de injeção de dependências.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-05
 */
@Service
public class UserService {

    /**
     * Repositório de dados do usuário, utilizado para operações de persistência.
     */
    private final UserRepository userRepository;

    /**
     * Construtor que injeta o repositório de usuários via autowired (injeção de dependência do Spring).
     *
     * @param userRepository O repositório que será utilizado para interações com o banco de dados.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Salva um novo usuário no banco de dados se ele não existir.
     *
     * O método verifica se já existe um usuário com o e-mail fornecido.
     * Se não existir, ele cria um novo usuário com o nome e e-mail fornecidos e atribui a função padrão 'ROLE_USER'.
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
}
