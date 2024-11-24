package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.model.PagamentoModel;

/**
 * Repositório para gerenciar operações de persistência da entidade {@link PagamentoModel}.
 * <p>Estende {@link JpaRepository} para fornecer métodos básicos de CRUD.</p>
 */
@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, Integer> {

}
