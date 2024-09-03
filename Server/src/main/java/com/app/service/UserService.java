package com.app.service;

import com.app.model.Endereco;
import com.app.model.User;
import com.app.repository.EnderecoRepository;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar usuários no sistema de e-commerce.
 *
 * <p>Esta classe fornece métodos para realizar operações CRUD (Create, Read, Update, Delete)
 * no repositório de usuários.</p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final EnderecoRepository enderecoRepository;

    /**
     * Construtor para injeção de dependência dos repositórios de usuários e endereços.
     *
     * @param userRepository O repositório de usuários a ser injetado.
     * @param enderecoRepository O repositório de endereços a ser injetado.
     */
    @Autowired
    public UserService(UserRepository userRepository, EnderecoRepository enderecoRepository) {
        this.userRepository = userRepository;
        this.enderecoRepository = enderecoRepository;
    }

    /**
     * Obtém todos os usuários do sistema.
     *
     * @return Uma lista contendo todos os usuários.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Obtém um usuário específico pelo seu identificador.
     *
     * @param id O identificador do usuário.
     * @return Um {@link Optional} contendo o usuário se encontrado, ou vazio se não encontrado.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Cria um novo usuário no sistema. Também persiste o endereço associado, se fornecido.
     *
     * @param user O usuário a ser criado.
     * @return O usuário criado.
     */
    public User createUser(User user) {
        if (user.getEndereco() != null) {
            Endereco endereco = user.getEndereco();
            if (endereco.getId() == null) {
                enderecoRepository.save(endereco);
            }
        }
        return userRepository.save(user);
    }

    /**
     * Atualiza um usuário existente com o novo valor fornecido.
     *
     * @param id O identificador do usuário a ser atualizado.
     * @param user O usuário com as novas informações.
     * @return Um {@link Optional} contendo o usuário atualizado se o identificador existir, ou vazio se não encontrado.
     */
    public Optional<User> updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            // Atualiza o endereço se estiver presente
            if (user.getEndereco() != null) {
                Endereco endereco = user.getEndereco();
                if (endereco.getId() == null) {
                    enderecoRepository.save(endereco);
                }
            }
            user.setId(id);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    /**
     * Exclui um usuário pelo seu identificador.
     *
     * @param id O identificador do usuário a ser excluído.
     * @return {@code true} se o usuário foi excluído com sucesso, {@code false} caso contrário.
     */
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
