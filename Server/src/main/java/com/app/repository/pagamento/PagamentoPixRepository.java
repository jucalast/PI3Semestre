package com.app.repository.pagamento;

import com.app.model.pagamento.PagamentoPixModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoPixRepository extends JpaRepository<PagamentoPixModel, String> {
}
