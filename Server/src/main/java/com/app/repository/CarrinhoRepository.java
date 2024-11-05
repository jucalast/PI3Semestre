package com.app.repository;

import com.app.model.Carrinho;
import com.app.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    List<Carrinho> findByUserModelId(Long idUser);

    boolean existsByUserModelIdAndProdutoId(Long idUser, Long productId);

    Optional<Carrinho> findByUserModelIdAndProdutoId(Long userModelId, Long produtoId);

    void deleteByUserModelIdAndProdutoId(Long idUser, Long productId);
}

