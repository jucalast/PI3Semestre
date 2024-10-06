package com.app.service;

import com.app.model.Produto;
import com.app.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
;

    @Transactional
    public Produto criarProduto(Produto produto, Long cafeEspecialId, Long metodoPreparoId) {
        // Verificar se café especial e método de preparo foram fornecidos
        if (cafeEspecialId != null && metodoPreparoId != null) {
            throw new IllegalArgumentException("O produto não pode estar associado a ambos, Café Especial e Método de Preparo.");
        }

        return produtoRepository.save(produto);
    }

}
