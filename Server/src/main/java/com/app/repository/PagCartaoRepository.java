package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.PagCartaoModel;
import com.app.model.PagamentoModel;

/**
 * Repositório para a entidade 'PagCartaoModel'.
 * Este repositório fornece operações CRUD para a tabela 'pagCartao'.
 * 
 * A anotação @Repository indica que esta interface é um repositório Spring,
 * facilitando a injeção de dependências e a configuração do Spring Data JPA.
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
     * @param id ID do pagamento associado.
     * @return Optional de PagCartaoModel, que pode conter o cartão correspondente se encontrado.
     */
    Optional<PagCartaoModel> findByid(Long id);

    /**
     * Conta quantos registros de PagCartao existem para uma determinada bandeira.
     * 
     * @param bandeiraCartao A bandeira do cartão.
     * @return Número de registros encontrados para a bandeira especificada.
     */
    long countByBandeiraCartao(String bandeiraCartao);

    /**
     * Encontra um PagCartao pelo CPF do titular do cartão.
     * 
     * @param cpf CPF do titular.
     * @return Optional de PagCartaoModel, que pode conter o cartão correspondente se encontrado.
     */
    Optional<PagCartaoModel> findByCpf(String cpf);

    /**
     * Busca todos os registros de PagCartao com base no número de parcelas.
     * 
     * @param parcelas Número de parcelas.
     * @return Lista de PagCartaoModel que correspondem ao número de parcelas especificado.
     */
    List<PagCartaoModel> findByParcelas(Integer parcelas);
    
    /**
     * Busca todos os registros de PagCartao com base na bandeira do cartão.
     * 
     * @param bandeira A bandeira do cartão.
     * @return Lista de PagCartaoModel que correspondem à bandeira especificada.
     */
    List<PagCartaoModel> findByBandeiraCartao(String bandeira);

    /**
     * Método personalizado para buscar PagCartao e retornar informações sobre a autorização do cartão.
     * 
     * @param autorizacaoCod Código de autorização.
     * @return Optional de PagCartaoModel, que pode conter o cartão correspondente se encontrado.
     */
    @Query("SELECT p FROM PagCartaoModel p WHERE p.autorizacaoCod = ?1")
    Optional<PagCartaoModel> findByAutorizacaoCod(String autorizacaoCod);

    /**
     * Método personalizado para buscar um PagCartao e retornar as informações sobre o pagamento associado.
     * 
     * @param pagamentoId ID do pagamento associado, representado pelo objeto PagamentoModel.
     * @return Optional de PagCartaoModel que pode conter o pagamento com cartão correspondente, se encontrado.
     */
    @Query("SELECT p FROM PagCartaoModel p WHERE p.pagamentoId = ?1")
    Optional<PagCartaoModel> findByPagamentoId(PagamentoModel pagamentoId);
}
