
package com.app.repository;

import com.app.model.CafeEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeEspecialRepository extends JpaRepository<CafeEspecial, Long> {
    // Métodos personalizados, se necessário
}
