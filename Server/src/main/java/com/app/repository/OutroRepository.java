package com.app.repository;

import com.app.model.Outro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutroRepository extends JpaRepository<Outro, Long> {
    // Métodos personalizados, se necessário
}
