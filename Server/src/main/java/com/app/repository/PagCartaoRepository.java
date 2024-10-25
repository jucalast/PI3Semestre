package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.PagCartaoModel;

/**
 * Repositório para a entidade 'PagCartaoModel'.
 * Este repositório fornece operações CRUD para a tabela 'pagCartao'.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 */
@Repository
public interface PagCartaoRepository extends JpaRepository<PagCartaoModel, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui, se necessário
}
