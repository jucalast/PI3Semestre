package com.app.repository;

import com.app.model.FavoritesModel;
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
}
