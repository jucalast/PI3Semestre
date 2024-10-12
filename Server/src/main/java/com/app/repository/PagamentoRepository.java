package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Pagamento;

/**
 * Interface do repositório para a entidade Pagamento.
 * Extende JpaRepository para herdar métodos de acesso a dados.
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    // Aqui você pode adicionar métodos personalizados, se necessário
}
