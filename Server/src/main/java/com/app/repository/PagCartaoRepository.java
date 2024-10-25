package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.PagCartaoModel;
import com.app.model.PagamentoModel;

/**
 * Repositório para a entidade 'PagCartaoModel'.
 */
@Repository
public interface PagCartaoRepository extends JpaRepository<PagCartaoModel, Long> {
    
    /**
     * Busca todos os registros de cartões associados a um pagamento específico.
     * 
     * @param pagamentoId O ID do pagamento associado.
     * @return Lista de registros de cartões vinculados ao pagamento.
     */
    List<PagCartaoModel> findByPagamentoId(Long pagamentoId);

    /**
     * Busca um registro de cartão pelo número do cartão (deve ser usado com cautela).
     * 
     * @param numero O número do cartão.
     * @return O registro de cartão correspondente ao número fornecido.
     */
    PagCartaoModel findByNumero(String numero);

    /**
     * Busca todos os registros de cartões com base no número de parcelas.
     * 
     * @param parcelas O número de parcelas.
     * @return Lista de registros de cartões com o número de parcelas fornecido.
     */
    List<PagCartaoModel> findByParcelas(Integer parcelas);

    /**
     * Verifica se um cartão está associado a um pagamento.
     * 
     * @param numero O número do cartão.
     * @return true se o cartão está associado a um pagamento; caso contrário, false.
     */
    boolean existsByNumero(String numero);

    /**
     * Verifica se um cartão está associado a um pagamento.  
     * @param pagamento O pagamento.
     * @return true se o cartão está associado ao pagamento; caso contrário, false.  
     */
    List<PagCartaoModel> findByPagamento(PagamentoModel pagamento);

      
}
