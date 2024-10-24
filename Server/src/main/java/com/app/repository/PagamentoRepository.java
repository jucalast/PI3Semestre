package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.PagamentoModel;

/**
 * Repositório responsável por realizar as operações de persistência relacionadas à
 * entidade 'PagamentoModel'.
 */
@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, Long> {

    /**
     * Busca uma lista de pagamentos com base no status de pagamento.
     * 
     * @param statusPagamento O status dos pagamentos a serem buscados (1 = PAGO, 2 = PENDENTE, 3 = CANCELADO).
     * @return Lista de pagamentos com o status fornecido.
     */
    List<PagamentoModel> findByStatusPagamento(Integer statusPagamento);

    /**
     * Busca todos os pagamentos associados a um pedido específico.
     * 
     * @param pedidoId O ID do pedido para o qual os pagamentos estão associados.
     * @return Lista de pagamentos vinculados ao pedido.
     */
    List<PagamentoModel> findByPedidoId(Long pedidoId);

    /**
     * Busca todos os pagamentos realizados por meio de um determinado método de pagamento.
     * 
     * @param metodoPagamentoId O ID do método de pagamento.
     * @return Lista de pagamentos vinculados ao método de pagamento.
     */
    List<PagamentoModel> findByMetodoPagamentoId(Long metodoPagamentoId);

    /**
     * Busca o pagamento mais recente.
     * 
     * @return O pagamento mais recente ou null se não houver pagamentos.
     */
    PagamentoModel findTopByOrderByDataHoraDesc();

    /**
     * Conta o número de pagamentos em um determinado status.
     * 
     * @param statusPagamento O status dos pagamentos a serem contados.
     * @return O número de pagamentos com o status fornecido.
     */
    long countByStatusPagamento(Integer statusPagamento);
}
