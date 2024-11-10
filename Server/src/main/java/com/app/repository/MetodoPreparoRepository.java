package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.MetodoPreparo;

/**
 * Repositório para a entidade MetodoPreparo, fornecendo métodos para realizar
 * operações de persistência. Extende JpaRepository para herdar operações
 * básicas de CRUD.
 *
 * @author João
 * @version 1.0
 * @since 2024-10-05
 */
public interface MetodoPreparoRepository extends JpaRepository<MetodoPreparo, Long> {

    /**
     * Encontra um MetodoPreparo com base no ID do produto associado.
     *
     * @param id o ID do produto
     * @return o MetodoPreparo associado ao produto, ou null se não encontrado
     */
    MetodoPreparo findByProdutoId(Long id);
}
