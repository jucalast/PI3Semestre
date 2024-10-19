package com.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.CupomModel;

/**
 * Interface responsável pela comunicação com o banco de dados para operações relacionadas à entidade CupomModel.
 * Esta interface herda de JpaRepository, que fornece operações básicas de CRUD (Create, Read, Update, Delete).
 * 
 * <p>A anotação `@Repository` indica que esta interface é um componente do Spring que gerencia a persistência de dados.</p>
 * 
 * <ul>
 *   <li>**findByNome**: Permite buscar cupons pelo nome.</li>
 *   <li>**findByDataValidade**: Permite buscar cupons com base na data de validade.</li>
 * </ul>
 * 
 * <p>Esses métodos utilizam convenções de nomenclatura do Spring Data JPA, que automaticamente cria as consultas com base nos nomes dos métodos.</p>
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-15
 */
@Repository
public interface CupomRepository extends JpaRepository<CupomModel, Integer> {

    /**
     * Busca uma lista de cupons com base no nome fornecido.
     * O método gera automaticamente a consulta SQL para encontrar cupons cujo nome corresponda ao parâmetro fornecido.
     * 
     * @param nome O nome do cupom que será utilizado para a busca.
     * @return Uma lista de CupomModel que contém o nome especificado.
     */
    List<CupomModel> findByNome(String nome);

    /**
     * Busca uma lista de cupons com base na data de validade fornecida.
     * O método gera automaticamente a consulta SQL para encontrar cupons cuja data de validade seja igual à fornecida.
     * 
     * @param dataValidade A data de validade a ser utilizada na busca.
     * @return Uma lista de CupomModel cuja data de validade corresponda à fornecida.
     */
    List<CupomModel> findByDataValidade(String dataValidade);
    /**
     * Este metodo lista todos os cupons dentro de um intervalo de tempo especificado.
     * @param dataInicio A data de inicio do intervalo de tempo.
     * @param dataFim A data de fim do intervalo de tempo.
     * @return Uma lista de CupomModel dentro do intervalo de tempo especificado.
     */
    @Query("SELECT c FROM CupomModel c WHERE c.dataValidade BETWEEN :dataInicio AND :dataFim")
    List<CupomModel> findByDataValidadeBetween(String dataInicio, String dataFim);
}

