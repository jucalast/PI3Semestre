package com.seu_pacote.repository;

import com.seu_pacote.model.CafeEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeEspecialRepository extends JpaRepository<CafeEspecial, Long> {
}
