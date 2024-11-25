package com.app.repository.pagamento;

import com.app.model.pagamento.PagamentoBoletoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoBoletoRepository extends JpaRepository<PagamentoBoletoModel, String> {
}
