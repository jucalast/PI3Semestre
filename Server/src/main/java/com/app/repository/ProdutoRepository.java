package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNome(String nome);
}
