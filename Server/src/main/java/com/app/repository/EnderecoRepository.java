package com.app.repository;

import com.app.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório de endereços para acesso ao banco de dados.
 */
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
