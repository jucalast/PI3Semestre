package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.PedidoModel;

/**
 * Repositório responsável por realizar operações de persistência
 * para a entidade PedidoModel. Extende JpaRepository, o que permite
 * o uso de métodos padrão para CRUD e consultas personalizadas.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-15
 */
@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    List<PedidoModel> findByDataPedidoBetween(String dataInicio, String dataFim);
    
    List<PedidoModel> findByStatusPedido(Integer statusPedido);

    @Query("SELECT p FROM PedidoModel p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim")
    List<PedidoModel> findPedidosByDataInterval(String dataInicio, String dataFim);
    
    // Adicionando método para encontrar pedidos por usuário (opcional)
    List<PedidoModel> findByUsuarioId(Integer usuarioId);
    
}
