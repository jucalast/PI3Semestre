package com.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.model.CafeEspecial;

import java.util.Set;

public interface CafeEspecialRepository extends CrudRepository<CafeEspecial, Long> {
    @SuppressWarnings("null")
    Set<CafeEspecial> findAllById(Iterable<Long> ids);
}
