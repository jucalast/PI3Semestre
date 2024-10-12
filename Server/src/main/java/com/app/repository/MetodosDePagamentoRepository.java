package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.MetodosDePagamentos;

@Repository
public interface MetodosDePagamentoRepository extends JpaRepository<MetodosDePagamentos, Integer> {
}

