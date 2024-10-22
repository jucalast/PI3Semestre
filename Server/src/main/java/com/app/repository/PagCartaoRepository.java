package com.app.repository;

/* 
* Importa a interface JpaRepository para operações de CRUD
* import org.springframework.data.jpa.repository.JpaRepository;
* 
* Importa a anotação @Repository para indicar que a interface é um repositório
* import org.springframework.stereotype.Repository;
*/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.PagCartaoModel;

/**
 * Repositório para a entidade 'PagCartaoModel'.
 * Esta interface estende JpaRepository, fornecendo métodos prontos
 * para realizar operações de CRUD (Create, Read, Update, Delete)
 * e consultas personalizadas no banco de dados.
 * 
 * A anotação @Repository indica que essa interface é um componente Spring que
 * interage com a camada de persistência, permitindo a injeção de dependência
 * e tratamento de exceções específicas do Spring.
 * 
 * JpaRepository já oferece implementações de métodos como salvar, deletar,
 * encontrar todos e encontrar por ID. Métodos adicionais de consulta podem
 * ser definidos aqui, se necessário.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-20
 */
@Repository
public interface PagCartaoRepository extends JpaRepository<PagCartaoModel, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui, se necessário.
}
