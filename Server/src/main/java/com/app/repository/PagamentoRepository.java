package com.app.repository;

import com.app.model.PagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, Integer> {

    // Consulta para encontrar pagamentos pelo status do pagamento
    List<PagamentoModel> findByStatusPagamento(PagamentoModel.StatusPagamento statusPagamento);

    // Consulta para encontrar pagamentos pelo ID do pedido
    List<PagamentoModel> findByPedidoId(Integer pedidoId);

    // Consulta para encontrar pagamentos pelo ID do cupom
    List<PagamentoModel> findByCupomId(Integer cupomId);
}
