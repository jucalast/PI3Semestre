package com.app.repository;

import com.app.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para a entidade Carrinho.
 */
@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
