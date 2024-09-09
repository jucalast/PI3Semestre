package com.app.service;

import com.app.model.Produto;
import com.app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto saveProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Optional<Produto> getProdutoById(Long id) {
        return produtoRepository.findById(id);
    }

    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    // Outros métodos de serviço podem ser adicionados aqui
}
