package com.app.repository;

import com.app.model.pagamento.PagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para gerenciar operações de persistência da entidade {@link PagamentoModel}.
 * <p>Estende {@link JpaRepository} para fornecer métodos básicos de CRUD.</p>
 */
@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, String> {
}
