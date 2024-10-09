package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.CafeEspecial;

public interface CafeEspecialRepository extends JpaRepository<CafeEspecial, Long> {

    // Modified to return a list
    List<CafeEspecial> findByProdutoId(Long id);

    List<CafeEspecial> findAllByProdutoIsNotNull();
}
