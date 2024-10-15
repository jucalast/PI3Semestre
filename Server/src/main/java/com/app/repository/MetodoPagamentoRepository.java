package com.app.repository;

/**
 * Bibliotecas utilizadas:
 * - org.springframework.data.jpa.repository.JpaRepository: fornece métodos prontos para operações básicas de banco de dados (CRUD) com JPA.
 * - org.springframework.stereotype.Repository: marca a interface como um repositório Spring, facilitando a injeção de dependência.
 * - java.util.List: representa uma lista de objetos, usada aqui para retornar uma lista de métodos de pagamento com base no nome.
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.MetodoPagamentoModel;

/**
 * Interface responsável por interagir com o banco de dados para operações relacionadas à entidade MetodoPagamentoModel.
 * 
 * Estende JpaRepository, o que fornece métodos prontos para operações CRUD (create, read, update, delete) e consultas no banco de dados.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-14
 */
@Repository
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamentoModel, Integer> {

    /**
     * Busca uma lista de métodos de pagamento pelo nome.
     * Este método cria uma consulta automática com base no nome passado como parâmetro.
     *
     * @param nome O nome do método de pagamento (ex: Boleto, Crédito, Pix, Débito).
     * @return Uma lista de MetodoPagamentoModel que correspondem ao nome fornecido.
     */
    List<MetodoPagamentoModel> findByNome(String nome);
    /**
     * Retorna uma lista de métodos de pagamento contendo apenas os campos 'nome' e 'taxa',
     * ordenados em ordem crescente de nome e taxa.
     * 
     * @return Uma lista de arrays de objetos, onde cada array contém 'nome' e 'taxa'.
     */
    @Query("SELECT m.nome, m.taxa FROM MetodoPagamentoModel m ORDER BY m.nome ASC, m.taxa ASC")
    List<Object[]> listPorNomeTaxa();
}
