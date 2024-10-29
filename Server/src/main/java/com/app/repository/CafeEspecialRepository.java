package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.CafeEspecial;

/**
 * Repositório para a entidade CafeEspecial, fornecendo métodos para realizar
 * operações de persistência. Extende JpaRepository para herdar operações
 * básicas de CRUD.
 *
 * @author João
 * @version 1.0
 * @since 2024-10-05
 */
public interface CafeEspecialRepository extends JpaRepository<CafeEspecial, Long> {

    /**
     * Encontra uma lista de CafeEspecial com base no ID do produto associado.
     *
     * @param id o ID do produto
     * @return uma lista de CafeEspecial associados ao produto
     */
    List<CafeEspecial> findByProdutoId(Long id);

    /**
     * Encontra todos os CafeEspecial que possuem um produto associado.
     *
     * @return uma lista de CafeEspecial com produto não nulo
     */
    List<CafeEspecial> findAllByProdutoIsNotNull();
}
