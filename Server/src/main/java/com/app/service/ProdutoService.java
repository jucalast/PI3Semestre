package com.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exceptions.ValidationUtil;
import com.app.model.Produto;
import com.app.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    // Criar um novo produto
    @Transactional
    public Produto createProduto(Produto produto) {
        logger.info("Criando um novo produto: {}", produto.getNome());

        // Validação do objeto inteiro
        ValidationUtil.validarObjeto(produto);

        // Verificar se o nome do produto já existe
        if (produtoRepository.existsByNome(produto.getNome())) {
            logger.error("Erro: Já existe um produto com o nome {}", produto.getNome());
            throw new RuntimeException("Já existe um produto com o nome: " + produto.getNome());
        }

        Produto savedProduto = produtoRepository.save(produto);
        logger.info("Produto {} criado com sucesso", savedProduto.getNome());
        return savedProduto;
    }

    // Listar todos os produtos
    public List<Produto> getAllProdutos() {
        logger.info("Listando todos os produtos");
        return produtoRepository.findAll();
    }

    // Obter um produto por ID
    public Produto getProdutoById(Long id) {
        logger.info("Buscando produto com ID: {}", id);
        return produtoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Produto não encontrado com ID: {}", id);
                    return new RuntimeException("Produto não encontrado com ID: " + id);
                });
    }

    // Atualizar um produto
    @Transactional
    public Produto updateProduto(Long id, Produto produto) {
        logger.info("Atualizando produto com ID: {}", id);

        // Validação do objeto inteiro
        ValidationUtil.validarObjeto(produto);

        // Verificar se o produto existe
        Produto existingProduto = getProdutoById(id);

        // Verificar se o nome do produto já existe, exceto para o próprio produto
        if (!existingProduto.getNome().equals(produto.getNome()) && produtoRepository.existsByNome(produto.getNome())) {
            logger.error("Erro: Já existe um produto com o nome {}", produto.getNome());
            throw new RuntimeException("Já existe um produto com o nome: " + produto.getNome());
        }

        // Atualizar os campos necessários
        existingProduto.setNome(produto.getNome());
        existingProduto.setDescricao(produto.getDescricao());
        existingProduto.setPreco(produto.getPreco());
        existingProduto.setImagem(produto.getImagem());
        existingProduto.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        existingProduto.setAvaliacao(produto.getAvaliacao());

        Produto updatedProduto = produtoRepository.save(existingProduto);
        logger.info("Produto {} atualizado com sucesso", updatedProduto.getNome());
        return updatedProduto;
    }

    // Deletar um produto
    @Transactional
    public void deleteProduto(Long id) {
        logger.info("Deletando produto com ID: {}", id);

        // Verificar se o produto existe antes de deletar
        if (!produtoRepository.existsById(id)) {
            logger.error("Erro: Produto não encontrado com ID: {}", id);
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }

        produtoRepository.deleteById(id);
        logger.info("Produto com ID {} deletado com sucesso", id);
    }
}
