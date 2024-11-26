package com.app.repository.pagamento;

import com.app.model.pagamento.PagamentoDebitoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoDebitoRepository extends JpaRepository<PagamentoDebitoModel, String> {
}
