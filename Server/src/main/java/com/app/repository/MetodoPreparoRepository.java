package com.app.repository;

import com.app.model.MetodoPreparo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPreparoRepository extends JpaRepository<MetodoPreparo, Long> {
}
