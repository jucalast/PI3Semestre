package com.app.repository;

import com.app.model.PagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Interface que representa o repositório de pagamentos.
 * Esta interface estende JpaRepository, fornecendo métodos para operações CRUD e consultas personalizadas
 * relacionadas à entidade PagamentoModel.
 * 
 * A anotação @Repository indica que esta interface é um repositório Spring,
 * facilitando a injeção de dependências e a configuração do Spring Data JPA.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-14
 */
@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, Integer> {

    /**
     * Consulta para encontrar pagamentos pelo status do pagamento.
     *
     * @param statusPagamento O status do pagamento a ser filtrado.
     * @return Lista de pagamentos que possuem o status especificado.
     */
    List<PagamentoModel> findByStatusPagamento(PagamentoModel.StatusPagamento statusPagamento);

    /**
     * Consulta para encontrar pagamentos pelo ID do pedido.
     *
     * @param pedidoId O ID do pedido associado ao pagamento.
     * @return Lista de pagamentos que pertencem ao pedido especificado.
     */
    List<PagamentoModel> findByPedidoId(Integer pedidoId);

    /**
     * Consulta para encontrar pagamentos pelo ID do cupom.
     *
     * @param cupomId O ID do cupom associado ao pagamento.
     * @return Lista de pagamentos que utilizam o cupom especificado.
     */
    List<PagamentoModel> findByCupomId(Integer cupomId);

    /**
     * Retorna o último pagamento cadastrado.
     *
     * @return O último pagamento cadastrado, ou null se não houver pagamentos.
     */
    PagamentoModel findTopByOrderByIdDesc(); // Corrigido para retornar a entidade
}
