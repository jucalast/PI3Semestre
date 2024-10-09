package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.MetodoPreparo;

public interface MetodoPreparoRepository extends JpaRepository<MetodoPreparo, Long> {

    // Adicione este método
    MetodoPreparo findByProdutoId(Long id);
}
