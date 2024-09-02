package com.seu_pacote.repository;

import com.seu_pacote.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui, se necessário
}
