package com.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exceptions.ValidationUtil;
import com.app.model.CafeEspecial;
import com.app.model.MetodoPreparo;
import com.app.model.Produto;
import com.app.repository.CafeEspecialRepository;
import com.app.repository.MetodoPreparoRepository;
import com.app.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;


    public List<Produto> filtrarProdutos(String nome, BigDecimal precoMin, BigDecimal precoMax, 
                                         String origem, String variedade, String torrefacao, 
                                         String tipoPreparo, MetodoPreparo.Complexidade complexidade) {
        
        return produtoRepository.filtrarPorAtributos(nome, precoMin, precoMax, origem, variedade, torrefacao, tipoPreparo, complexidade);
    }

    @Transactional
    public Produto createProduto(Produto produto) {
        logger.info("Criando um novo produto: {}", produto.getNome());

        ValidationUtil.validarObjeto(produto);

        if (produtoRepository.existsByNome(produto.getNome())) {
            logger.error("Erro: Já existe um produto com o nome {}", produto.getNome());
            throw new RuntimeException("Já existe um produto com o nome: " + produto.getNome());
        }

        // Salva o produto e suas especializações
        Produto savedProduto = produtoRepository.save(produto);
        logger.info("Produto {} criado com sucesso", savedProduto.getNome());
        return savedProduto;
    }

    public List<Produto> getAllProdutos() {
        logger.info("Listando todos os produtos");
        List<Produto> produtos = produtoRepository.findAll();

        // Inicializa as especializações para cada produto
        produtos.forEach(produto -> {
            Hibernate.initialize(produto.getCafeEspecial());
            Hibernate.initialize(produto.getMetodoPreparo());
        });

        return produtos;
    }

    public List<Produto> getAllProdutosComEspecializacoes() {
        logger.info("Listando todos os produtos com suas especializações");
        List<Produto> produtos = produtoRepository.findAll();
        // Aqui, não é necessário usar Hibernate.initialize, porque estamos usando EAGER
        return produtos;
    }

    public List<Produto> searchProdutosByNome(String nome) {
        logger.info("Buscando produtos com o nome: {}", nome);
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);

        if (produtos.isEmpty()) {
            logger.warn("Nenhum produto encontrado com o nome: {}", nome);
        }

        // Inicializa as especializações para cada produto
        produtos.forEach(produto -> {
            Hibernate.initialize(produto.getCafeEspecial());
            Hibernate.initialize(produto.getMetodoPreparo());
        });

        return produtos;
    }

    public Produto getProdutoById(Long id) {
        logger.info("Buscando produto com ID: {}", id);
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Produto não encontrado com ID: {}", id);
                    return new RuntimeException("Produto não encontrado com ID: " + id);
                });

        // Asegure-se de que as especializações estão sendo carregadas
        Hibernate.initialize(produto.getCafeEspecial());
        Hibernate.initialize(produto.getMetodoPreparo());

        return produto;
    }

    @Transactional
    public Produto updateProduto(Long id, Produto produto) {
        logger.info("Atualizando produto com ID: {}", id);

        ValidationUtil.validarObjeto(produto);

        Produto existingProduto = getProdutoById(id);

        if (!existingProduto.getNome().equals(produto.getNome()) && produtoRepository.existsByNome(produto.getNome())) {
            logger.error("Erro: Já existe um produto com o nome {}", produto.getNome());
            throw new RuntimeException("Já existe um produto com o nome: " + produto.getNome());
        }

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

    @Transactional
    public void deleteProduto(Long id) {
        logger.info("Deletando produto com ID: {}", id);

        if (!produtoRepository.existsById(id)) {
            logger.error("Erro: Produto não encontrado com ID: {}", id);
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }

        produtoRepository.deleteById(id);
        logger.info("Produto com ID {} deletado com sucesso", id);
    }


    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

    @Autowired
    private MetodoPreparoRepository metodoPreparoRepository;

    public List<Map<String, Object>> listarAtributos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<Map<String, Object>> listaAtributos = new ArrayList<>();

        for (Produto produto : produtos) {
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("avaliacao", produto.getAvaliacao());

            // Verifica se o produto possui CafeEspecial associado
            CafeEspecial cafeEspecial = produto.getCafeEspecial();
            if (cafeEspecial != null) {
                atributos.put("notasSensoriais", cafeEspecial.getNotasSensoriais());
                atributos.put("origem", cafeEspecial.getOrigem());
                atributos.put("recomendacoesPreparo", cafeEspecial.getRecomendacoesPreparo());
                atributos.put("torra", cafeEspecial.getTorra());
                atributos.put("torrefacao", cafeEspecial.getTorrefacao());
                atributos.put("variedade", cafeEspecial.getVariedade());
            }

            // Verifica se o produto possui MetodoPreparo associado
            MetodoPreparo metodoPreparo = produto.getMetodoPreparo();
            if (metodoPreparo != null) {
                atributos.put("complexidade", metodoPreparo.getComplexidade());
                atributos.put("marca", metodoPreparo.getMarca());
                atributos.put("material", metodoPreparo.getMaterial());
                atributos.put("tipoPreparo", metodoPreparo.getTipoPreparo());
            }

            listaAtributos.add(atributos);
        }

        return listaAtributos;
    }
}
