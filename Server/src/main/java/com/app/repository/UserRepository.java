package com.app.repository;

import com.app.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface responsável por realizar operações de persistência relacionadas à entidade UserModel.
 * Extende JpaRepository para fornecer métodos básicos de CRUD e consultas personalizadas.
 *
 * A anotação @Repository indica que esta interface é um componente do Spring que interage com o banco de dados.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-05
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    /**
     * Busca um usuário no banco de dados pelo endereço de e-mail.
     *
     * @param emailId O e-mail do usuário a ser buscado.
     * @return O modelo de usuário correspondente ao e-mail fornecido, ou null se não for encontrado.
     */
    UserModel findByEmailId(String emailId);
}
