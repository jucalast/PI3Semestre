package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNome(String nome);

}
