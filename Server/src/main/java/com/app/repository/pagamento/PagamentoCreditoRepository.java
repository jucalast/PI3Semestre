package com.app.repository.pagamento;

import com.app.model.pagamento.PagamentoCreditoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoCreditoRepository extends JpaRepository<PagamentoCreditoModel, String> {
}
