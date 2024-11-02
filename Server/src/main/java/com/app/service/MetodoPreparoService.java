package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exceptions.ValidationUtil;
import com.app.model.MetodoPreparo;
import com.app.model.Produto;
import com.app.repository.MetodoPreparoRepository;

/**
 * Serviço para gerenciar operações relacionadas a Métodos de Preparo. Esta
 * classe contém métodos para criar um novo Método de Preparo e buscar métodos
 * de preparo associados a produtos.
 *
 * @author João
 * @version 1.0
 * @since 2024-10-05
 */
@Service
public class MetodoPreparoService {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private MetodoPreparoRepository metodoPreparoRepository;

    /**
     * Cria um novo Método de Preparo após validar o objeto e seu produto
     * associado.
     *
     * @param metodoPreparo o Método de Preparo a ser criado
     * @return o Método de Preparo criado
     * @throws IllegalArgumentException se o objeto ou o produto não for válido
     */
    public MetodoPreparo createMetodoPreparo(MetodoPreparo metodoPreparo) {
        // Validação do objeto inteiro
        ValidationUtil.validarObjeto(metodoPreparo);

        Produto produto = metodoPreparo.getProduto();
        Produto savedProduto = produtoService.createProduto(produto);

        metodoPreparo.setProduto(savedProduto);

        return metodoPreparoRepository.save(metodoPreparo);
    }

    /**
     * Busca o Método de Preparo associado ao ID do produto especificado.
     *
     * @param id o ID do produto
     * @return o Método de Preparo associado ao produto, ou null se não
     * encontrado
     */
    public MetodoPreparo buscarMetodoPreparoPorProdutoId(Long id) {
        return metodoPreparoRepository.findByProdutoId(id);
    }
}
