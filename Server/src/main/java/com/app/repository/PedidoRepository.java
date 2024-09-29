package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Pedido;

/**
 * Repositório para a entidade Pedido.
 * A interface JpaRepository já fornece métodos prontos para operações CRUD básicas.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Métodos adicionais podem ser declarados aqui, se necessário
    // Exemplo: List<Pedido> findByStatusPedido(String statusPedido);
}
