package com.app.repository;

import com.app.model.FavoritesModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for handling persistence operations of FavoritesModel entities.
 */
@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesModel, Long> {
    List<FavoritesModel> findByUserId(Long userId);
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    // Método para remover um favorito específico
    @Transactional
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
