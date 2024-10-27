package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.PagCartaoModel;

/**
 * Repositório para a entidade 'PagCartaoModel'.
 * Este repositório fornece operações CRUD para a tabela 'pagCartao'.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 */
@Repository
public interface PagCartaoRepository extends JpaRepository<PagCartaoModel, Long> {

    /**
     * Encontra um PagCartao pelo ID do pagamento associado.
     * 
     * @param pagamentoId ID do pagamento associado
     * @return Optional de PagCartaoModel
     */
    Optional<PagCartaoModel> findByPagamentoId(Long pagamentoId);

    /**
     * Conta quantos registros de PagCartao existem para uma determinada bandeira.
     * 
     * @param bandeiraCartao A bandeira do cartão
     * @return Número de registros encontrados
     */
    long countByBandeiraCartao(String bandeiraCartao);

    /**
     * Encontra um PagCartao pelo CPF do titular do cartão.
     * 
     * @param cpf CPF do titular
     * @return Optional de PagCartaoModel
     */
    Optional<PagCartaoModel> findByCpf(String cpf);

    /**
     * Busca todos os registros de PagCartao com base no número de parcelas.
     * 
     * @param parcelas Número de parcelas
     * @return Lista de PagCartaoModel
     */
    List<PagCartaoModel> findByParcelas(Integer parcelas);
    
    /**
     * Busca todos os registros de PagCartao com base na bandeira do cartão.
     * 
     * @param bandeira A bandeira do cartão
     * @return Lista de PagCartaoModel
     */
    List<PagCartaoModel> findByBandeiraCartao(String bandeira);

    /**
     * Método personalizado para buscar PagCartao e retornar informações sobre a autorização do cartão.
     * 
     * @param autorizacaoCod Código de autorização
     * @return Optional de PagCartaoModel
     */
    @Query("SELECT p FROM PagCartaoModel p WHERE p.autorizacaoCod = ?1")
    Optional<PagCartaoModel> findByAutorizacaoCod(String autorizacaoCod);
}
