package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Produto;

/**
 * Repositório para a entidade Produto, fornecendo métodos para realizar
 * operações de persistência. Extende JpaRepository para herdar operações
 * básicas de CRUD.
 *
 * Esta interface é marcada com @Repository para indicar que é um componente
 * Spring.
 *
 * @author João
 * @version 1.0
 * @since 2024-10-05
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Verifica se um produto com o nome especificado já existe no repositório.
     *
     * @param nome o nome do produto a ser verificado
     * @return true se o produto existir, false caso contrário
     */
    boolean existsByNome(String nome);
}
