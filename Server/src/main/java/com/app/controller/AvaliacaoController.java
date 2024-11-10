package com.app.controller;

import com.app.model.AvaliacaoModel;
import com.app.model.ProdutoPedidoModel;
import com.app.model.UserModel;
import com.app.repository.ProdutoPedidoRepository;
import com.app.service.AvaliacaoService;
import com.app.repository.AvaliacaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Controlador para gerenciar as operações de avaliação.
 * Este controlador expõe endpoints REST para adicionar, listar e calcular média de avaliações.
 *
 * <p>Endereços disponíveis:
 * <ul>
 *   <li>/avaliacoes - Adiciona uma nova avaliação</li>
 *   <li>/avaliacoes/listar - Lista todas as avaliações com IDs</li>
 *   <li>/avaliacoes/{id} - Busca uma avaliação por ID</li>
 *   <li>/avaliacoes/media/{produtoId} - Calcula a média de avaliações para um produto</li>
 * </ul>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    /**
     * Serviço de avaliação usado para criar novas avaliações.
     */
    @Autowired
    private AvaliacaoService avaliacaoService;

    /**
     * Repositório de avaliações utilizado para buscar e listar avaliações.
     */
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    /**
     * Repositório de ProdutoPedido utilizado para buscar produtos associados a pedidos.
     */
    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    /**
     * Adiciona uma nova avaliação a um ProdutoPedido específico.
     *
     * @param request HttpServletRequest contendo informações da sessão do usuário.
     * @param produtoPedidoId ID do ProdutoPedido a ser avaliado.
     * @param nota Nota atribuída na avaliação.
     * @param descricao Descrição opcional da avaliação.
     * @return AvaliacaoModel representando a avaliação criada.
     */
    @PostMapping
    public AvaliacaoModel adicionarAvaliacao(HttpServletRequest request,
                                             @RequestParam int produtoPedidoId,
                                             @RequestParam int nota,
                                             @RequestParam(required = false) String descricao) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        Long userId = authenticatedUser.getId();

        return avaliacaoService.criarAvaliacao(produtoPedidoId, nota, descricao, userId);
    }

    /**
     * Lista todas as avaliações retornando somente os IDs de avaliação, usuário e ProdutoPedido.
     *
     * @return Lista de Mapas com informações sobre IDs de avaliação, usuário e ProdutoPedido.
     */
    @GetMapping("/listar")
    public List<Map<String, Object>> listarAvaliacoesIds() {
        List<AvaliacaoModel> avaliacoes = avaliacaoRepository.findAll();
        return avaliacoes.stream()
                .map(avaliacao -> {
                    Map<String, Object> idsMap = new HashMap<>();
                    idsMap.put("avaliacaoId", avaliacao.getId());
                    idsMap.put("userId", avaliacao.getUser().getId());
                    idsMap.put("produtoPedidoId", avaliacao.getProdutoPedido().getId());
                    return idsMap;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retorna informações detalhadas sobre uma avaliação específica.
     *
     * @param id ID da avaliação a ser buscada.
     * @return Mapa contendo nota, descrição e nome do usuário da avaliação.
     */
    @GetMapping("/{id}")
    public Map<String, Object> getAvaliacao(@PathVariable Integer id) {
        AvaliacaoModel avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Avaliação não encontrada para o ID: " + id));

        Map<String, Object> response = new HashMap<>();
        response.put("nota", avaliacao.getNota());
        response.put("descricao", avaliacao.getDescricao());
        response.put("userName", avaliacao.getUser().getUserName());

        return response;
    }

    /**
     * Calcula a média de avaliações de um produto específico.
     *
     * @param produtoId ID do produto para o qual calcular a média de avaliações.
     * @return Média das notas das avaliações do produto.
     */
    @GetMapping("/media/{produtoId}")
    public double calcularMediaDeAvaliacoes(@PathVariable Integer produtoId) {
        List<ProdutoPedidoModel> produtoPedidos = produtoPedidoRepository.findByProdutoId(produtoId);
        if (produtoPedidos.isEmpty()) {
            throw new NoSuchElementException("Nenhum ProdutoPedido encontrado para o produto ID: " + produtoId);
        }

        OptionalDouble media = produtoPedidos.stream()
                .mapToInt(produtoPedido -> avaliacaoRepository.findByProdutoPedidoId(produtoPedido.getId())
                        .map(AvaliacaoModel::getNota)
                        .orElse(0))
                .average();

        return media.orElseThrow(() -> new IllegalArgumentException("Não foram encontradas avaliações para os produtos pedidos."));
    }
}

