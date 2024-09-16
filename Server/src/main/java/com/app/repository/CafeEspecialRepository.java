package com.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.model.CafeEspecial;

import java.util.Set;

public interface CafeEspecialRepository extends CrudRepository<CafeEspecial, Long> {
    @SuppressWarnings("null")
    Set<CafeEspecial> findAllById(Iterable<Long> ids);
}

package com.app.repository;

import com.app.model.CafeEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeEspecialRepository extends JpaRepository<CafeEspecial, Long> {
    // JpaRepository already provides methods for basic CRUD operations
}
