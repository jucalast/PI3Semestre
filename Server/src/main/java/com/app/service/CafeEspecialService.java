package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exceptions.ValidationUtil;
import com.app.model.CafeEspecial;
import com.app.model.Produto;
import com.app.repository.CafeEspecialRepository;

/**
 * Serviço para gerenciar operações relacionadas a Café Especial. Esta classe
 * contém métodos para criar, listar e buscar Café Especial associado a
 * produtos.
 *
 * @author João
 * @version 1.0
 * @since 2024-10-05
 */
@Service
public class CafeEspecialService {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

    /**
     * Cria um novo Café Especial após validar o objeto e seu produto associado.
     *
     * @param cafeEspecial o Café Especial a ser criado
     * @return o Café Especial criado
     * @throws IllegalArgumentException se o objeto ou o produto não for válido
     */
    public CafeEspecial createCafeEspecial(CafeEspecial cafeEspecial) {
        // Validação do objeto inteiro
        ValidationUtil.validarObjeto(cafeEspecial);

        // Validar e salvar o produto associado
        Produto produto = cafeEspecial.getProduto();
        ValidationUtil.validarObjeto(produto); // Validação do produto

        Produto savedProduto = produtoService.createProduto(produto);
        cafeEspecial.setProduto(savedProduto);

        // Salvar o café especial após todas as validações
        return cafeEspecialRepository.save(cafeEspecial);
    }

    /**
     * Lista todos os Café Especiais que possuem produtos associados.
     *
     * @return uma lista de Café Especial
     */
    public List<CafeEspecial> listarCafesEspeciais() {
        return cafeEspecialRepository.findAllByProdutoIsNotNull();
    }

    /**
     * Encontra todos os Café Especiais associados ao ID do produto
     * especificado.
     *
     * @param id o ID do produto
     * @return uma lista de Café Especial associados ao produto
     */
    public List<CafeEspecial> findByProdutoId(Long id) {
        return cafeEspecialRepository.findByProdutoId(id);
    }

}
