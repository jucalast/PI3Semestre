package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exceptions.ValidationUtil;
import com.app.model.CafeEspecial;
import com.app.model.Produto;
import com.app.repository.CafeEspecialRepository;

@Service
public class CafeEspecialService {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

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

    public List<CafeEspecial> listarCafesEspeciais() {
        return cafeEspecialRepository.findAllByProdutoIsNotNull();
    }

    public List<CafeEspecial> findByProdutoId(Long id) {
        return cafeEspecialRepository.findByProdutoId(id);
    }

}
