package com.app.repository;

import com.app.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade AddressModel.
 * Esta interface estende JpaRepository para fornecer operações CRUD.
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {
    
}
