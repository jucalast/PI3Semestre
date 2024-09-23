package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.CafeEspecial;

@Repository
public interface CafeEspecialRepository extends JpaRepository<CafeEspecial, Long> {
}
