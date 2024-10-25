package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.PagamentoModel;

/**
 * Repositório responsável por realizar as operações de persistência relacionadas à
 * entidade 'PagamentoModel'. Este repositório fornece métodos padrão de CRUD e
 * pode ser extendido com consultas personalizadas.
 * 
 * A interface JpaRepository é utilizada para herdar operações básicas como
 * salvar, buscar, atualizar e deletar pagamentos no banco de dados.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
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

}
