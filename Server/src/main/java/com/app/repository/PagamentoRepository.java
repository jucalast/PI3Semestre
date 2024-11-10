package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.CupomModel;
import com.app.model.PagamentoModel;
import com.app.model.PedidoModel;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, Integer> {

    List<PagamentoModel> findByStatusPagamento(PagamentoModel.StatusPagamento statusPagamento);

    List<PagamentoModel> findByPedidoId(PedidoModel pedido);


    List<PagamentoModel> findByCupomId(CupomModel cupomId);

    PagamentoModel findTopByOrderByIdDesc();
}
