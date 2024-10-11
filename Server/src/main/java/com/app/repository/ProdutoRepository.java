package com.app.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.model.MetodoPreparo;
import com.app.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNome(String nome);

    // Método para buscar produtos pelo nome com correspondência parcial
    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produto> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT p FROM Produto p " +
            "LEFT JOIN p.cafeEspecial ce " +
            "LEFT JOIN p.metodoPreparo mp " +
            "WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "AND (:precoMin IS NULL OR p.preco >= :precoMin) " +
            "AND (:precoMax IS NULL OR p.preco <= :precoMax) " +
            "AND (:origem IS NULL OR LOWER(ce.origem) LIKE LOWER(CONCAT('%', :origem, '%'))) " +
            "AND (:variedade IS NULL OR LOWER(ce.variedade) LIKE LOWER(CONCAT('%', :variedade, '%'))) " +
            "AND (:torrefacao IS NULL OR LOWER(ce.torrefacao) LIKE LOWER(CONCAT('%', :torrefacao, '%'))) " +
            "AND (:tipoPreparo IS NULL OR LOWER(mp.tipoPreparo) LIKE LOWER(CONCAT('%', :tipoPreparo, '%'))) " +
            "AND (:complexidade IS NULL OR mp.complexidade = :complexidade)")
    List<Produto> filtrarPorAtributos(@Param("nome") String nome,
                                      @Param("precoMin") BigDecimal precoMin,
                                      @Param("precoMax") BigDecimal precoMax,
                                      @Param("origem") String origem,
                                      @Param("variedade") String variedade,
                                      @Param("torrefacao") String torrefacao,
                                      @Param("tipoPreparo") String tipoPreparo,
                                      @Param("complexidade") MetodoPreparo.Complexidade complexidade);
}
