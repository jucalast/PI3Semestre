package com.app.controller;

import com.app.model.Produto;
import com.app.service.ProdutoService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável pela gestão das rotas relacionadas ao produto.
 *
 * Este controlador manipula o CRUD de produtos, além de oferecer rotas
 * específicas para obter listas de produtos com atributos personalizados e
 * especializações.
 *
 * As rotas incluem operações de criação, leitura, atualização e exclusão (CRUD)
 * de produtos.
 *
 * @version 1.0
 * @since 2024-10-05
 */
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoService produtoService;

    /**
     * Rota que lista os valores dos atributos solicitados dos produtos.
     *
     * @return A lista de mapas contendo os valores dos atributos dos produtos.
     */
    @GetMapping("/atributos")
    public ResponseEntity<List<Map<String, Object>>> listarAtributos() {
        List<Map<String, Object>> atributos = produtoService.listarAtributos();
        return ResponseEntity.ok(atributos);
    }

    /**
     * Rota que lista todos os produtos.
     *
     * @return A lista completa de produtos.
     */
    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoService.getAllProdutos();
    }

    /**
     * Rota que lista todos os produtos com suas especializações carregadas.
     *
     * Este método garante que as associações, como `CafeEspecial` e
     * `MetodoPreparo`, estejam carregadas para cada produto.
     *
     * @return A lista completa de produtos com especializações carregadas.
     */
    @GetMapping("/com-especializacoes")
    public List<Produto> getAllProdutosComEspecializacoes() {
        List<Produto> produtos = produtoService.getAllProdutos();
        for (Produto produto : produtos) {
            Hibernate.initialize(produto.getCafeEspecial());
            Hibernate.initialize(produto.getMetodoPreparo());
        }
        return produtos;
    }

    /**
     * Rota que obtém um produto pelo seu ID.
     *
     * @param id O ID do produto a ser buscado.
     * @return O produto correspondente ao ID, ou uma resposta 404 caso o
     * produto não seja encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produto = Optional.ofNullable(produtoService.getProdutoById(id));
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Rota que obtém um produto pelo seu ID com especializações carregadas.
     *
     * @param id O ID do produto a ser buscado.
     * @return O produto correspondente ao ID, ou uma resposta 404 caso o
     * produto não seja encontrado.
     */
    @GetMapping("/com-especializacoes/{id}")
    public ResponseEntity<Produto> getProdutoComEspecializacoesById(@PathVariable Long id) {
        Optional<Produto> produto = Optional.ofNullable(produtoService.getProdutoById(id));
        if (produto.isPresent()) {
            Hibernate.initialize(produto.get().getCafeEspecial());
            Hibernate.initialize(produto.get().getMetodoPreparo());
            return ResponseEntity.ok(produto.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Rota para criar um novo produto.
     *
     * Caso o produto seja uma especialização, as associações são configuradas
     * corretamente antes de salvar.
     *
     * @param produto O objeto Produto a ser criado.
     * @return O produto recém-criado.
     */
    @PostMapping("/protected/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        
        if (produto.getCafeEspecial() != null) {
            produto.getCafeEspecial().setProduto(produto);
        } else if (produto.getMetodoPreparo() != null) {
            produto.getMetodoPreparo().setProduto(produto);
        }

        Produto savedProduto = produtoService.createProduto(produto);
        return ResponseEntity.ok(savedProduto);
    }

    /**
     * Rota para atualizar um produto existente.
     *
     * @param id O ID do produto a ser atualizado.
     * @param produto Os novos dados do produto.
     * @return O produto atualizado ou uma resposta 404 se o produto não for
     * encontrado.
     */
    @PutMapping("/protected/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Optional<Produto> existingProduto = Optional.ofNullable(produtoService.getProdutoById(id));
        if (existingProduto.isPresent()) {
            produto.setId(id);
            Produto updatedProduto = produtoService.updateProduto(id, produto);
            return ResponseEntity.ok(updatedProduto);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Rota para desativar um produto pelo seu ID.
     *
     * @param id O ID do produto a ser desativado.
     * @return O produto desativado ou uma resposta 404 se o produto não for encontrado.
     */
    @PutMapping("/protected/deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> deactivateProduto(@PathVariable Long id) {
        logger.info("Recebida solicitação para desativar produto com ID: {}", id);
        Optional<Produto> existingProduto = Optional.ofNullable(produtoService.getProdutoById(id));
        if (existingProduto.isPresent()) {
            Produto updatedProduto = produtoService.deactivateProduto(id);
            logger.info("Produto desativado com sucesso: {}", updatedProduto);
            return ResponseEntity.ok(updatedProduto);
        }
        logger.warn("Produto com ID {} não encontrado", id);
        return ResponseEntity.notFound().build();
    }

    /**
     * Rota para ativar um produto pelo seu ID.
     *
     * @param id O ID do produto a ser ativado.
     * @return O produto ativado ou uma resposta 404 se o produto não for encontrado.
     */
    @PutMapping("/protected/activate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> activateProduto(@PathVariable Long id) {
        logger.info("Recebida solicitação para ativar produto com ID: {}", id);
        Optional<Produto> existingProduto = Optional.ofNullable(produtoService.getProdutoById(id));
        if (existingProduto.isPresent()) {
            Produto updatedProduto = produtoService.activateProduto(id);
            logger.info("Produto ativado com sucesso: {}", updatedProduto);
            return ResponseEntity.ok(updatedProduto);
        }
        logger.warn("Produto com ID {} não encontrado", id);
        return ResponseEntity.notFound().build();
    }

    /**
     * Rota para excluir um produto pelo seu ID.
     *
     * @param id O ID do produto a ser excluído.
     * @return Uma resposta 204 se a exclusão for bem-sucedida ou uma resposta
     * 404 se o produto não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (produtoService.getProdutoById(id) != null) {
            produtoService.deleteProduto(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Rota para buscar produtos com base nos atributos fornecidos.
     *
     * @param atributos Um mapa de atributos para filtrar os produtos.
     * @return A lista de produtos que correspondem aos critérios de busca.
     */
    @GetMapping("/buscar-por-atributos")
    public ResponseEntity<List<Produto>> buscarProdutosPorAtributos(@RequestParam Map<String, String> atributos) {
        List<Produto> produtos = produtoService.buscarProdutosPorAtributos(atributos);
        return ResponseEntity.ok(produtos);
    }
}
