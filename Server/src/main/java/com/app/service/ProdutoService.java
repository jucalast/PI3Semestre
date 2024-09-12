package com.app.service;

import com.app.model.Produto;
import com.app.model.CafeEspecial;
import com.app.repository.ProdutoRepository;
import com.app.repository.CafeEspecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

    /**
     * Cria um novo produto e associa cafés especiais a ele.
     *
     * @param produto         O produto a ser criado.
     * @param cafeEspecialIds IDs dos cafés especiais a serem associados ao produto.
     * @return O produto criado.
     */
    public Produto createProduto(Produto produto, Set<Long> cafeEspecialIds) {
        Set<CafeEspecial> cafeEspeciais = cafeEspecialRepository.findAllById(cafeEspecialIds);
        produto.setCafeEspeciais(cafeEspeciais);
        return produtoRepository.save(produto);
    }
}
