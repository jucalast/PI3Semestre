package com.app.repository;

/* 
* Importa a interface JpaRepository para operações de persistência
* import org.springframework.data.jpa.repository.JpaRepository;
* 
* Importa a anotação @Repository do Spring para indicar que é um repositório
* import org.springframework.stereotype.Repository;
*/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.PagamentoModel;

/**
 * Repositório que gerencia as operações de persistência da entidade 'pagamentos'.
 * Esta interface estende JpaRepository, que fornece métodos prontos para CRUD (Create, Read, Update, Delete)
 * e consultas personalizadas.
 * 
 * A anotação @Repository é usada para marcar essa interface como um componente Spring que lida
 * com operações de acesso ao banco de dados.
 * 
 * JpaRepository já oferece métodos genéricos, como salvar, deletar e buscar todos os registros,
 * além de consultas paginadas e ordenadas.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-20
 */
@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, Long> {

   /**
    * Busca um pagamento no banco de dados com base no transactionId.
    * Esse método cria uma consulta personalizada para encontrar um pagamento específico
    * pelo seu identificador de transação (transactionId), que geralmente é gerado por sistemas
    * externos de pagamento.
    * 
    * @param transactionId Identificador único da transação.
    * @return PagamentoModel O modelo do pagamento correspondente ao transactionId.
    */
   PagamentoModel findByTransactionId(String transactionId);
}
