package com.app.service;

import com.app.model.CafeEspecial;
import com.app.model.MetodoPreparo;
import com.app.model.Produto;
import com.app.repository.CafeEspecialRepository;
import com.app.repository.MetodoPreparoRepository;
import com.app.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

    @Autowired
    private MetodoPreparoRepository metodoPreparoRepository;

    @Transactional
    public Produto criarProduto(Produto produto, Long cafeEspecialId, Long metodoPreparoId) {
        // Verificar se ambos IDs (cafeEspecialId e metodoPreparoId) foram fornecidos simultaneamente
        if (cafeEspecialId != null && metodoPreparoId != null) {
            throw new IllegalArgumentException("O produto não pode estar associado a ambos, Café Especial e Método de Preparo.");
        }

        // Associação de Café Especial
        if (cafeEspecialId != null) {
            CafeEspecial cafeEspecial = cafeEspecialRepository.findById(cafeEspecialId)
                    .orElseThrow(() -> new EntityNotFoundException("Café Especial não encontrado com ID: " + cafeEspecialId));
            produto.setCafeEspecial(cafeEspecial);
        }

        // Associação de Método de Preparo
        if (metodoPreparoId != null) {
            MetodoPreparo metodoPreparo = metodoPreparoRepository.findById(metodoPreparoId)
                    .orElseThrow(() -> new EntityNotFoundException("Método de Preparo não encontrado com ID: " + metodoPreparoId));
            produto.setMetodoPreparo(metodoPreparo);
        }

        // Garantir que o produto esteja associado a pelo menos um (Café Especial ou Método de Preparo)
        if (produto.getCafeEspecial() == null && produto.getMetodoPreparo() == null) {
            throw new IllegalArgumentException("O produto deve estar associado a um Café Especial ou a um Método de Preparo.");
        }

        // Salvamento do produto
        return produtoRepository.save(produto);
    }
}
