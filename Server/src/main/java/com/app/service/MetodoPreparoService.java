package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exceptions.ValidationUtil;
import com.app.model.MetodoPreparo;
import com.app.model.Produto;
import com.app.repository.MetodoPreparoRepository;

@Service
public class MetodoPreparoService {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private MetodoPreparoRepository metodoPreparoRepository;

    public MetodoPreparo createMetodoPreparo(MetodoPreparo metodoPreparo) {
        // Validação do objeto inteiro
        ValidationUtil.validarObjeto(metodoPreparo);

        Produto produto = metodoPreparo.getProduto();
        Produto savedProduto = produtoService.createProduto(produto);

        metodoPreparo.setProduto(savedProduto);

        return metodoPreparoRepository.save(metodoPreparo);
    }

    // Método para buscar método de preparo pelo id do produto
    public MetodoPreparo buscarMetodoPreparoPorProdutoId(Long id) {
        return metodoPreparoRepository.findByProdutoId(id);
    }
}
