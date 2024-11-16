package com.app.service;

import com.app.model.AvaliacaoModel;
import com.app.model.ProdutoPedidoModel;
import com.app.model.UserModel;
import com.app.repository.AvaliacaoRepository;
import com.app.repository.ProdutoPedidoRepository;
import com.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
/**
 * Serviço para gerenciar operações de criação de avaliações.
 *
 * <p>Este serviço permite criar uma avaliação associada a um ProdutoPedido e um usuário específico.</p>
 *
 * @since 1.0
 */
@Service
public class AvaliacaoService {

    /**
     * Repositório de avaliações para operações de persistência de avaliações.
     */
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    /**
     * Repositório de ProdutoPedido para buscar produtos associados a pedidos.
     */
    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    /**
     * Repositório de usuários para buscar informações de usuários.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Cria uma nova avaliação associada a um ProdutoPedido e um usuário específico.
     *
     * @param produtoPedidoId ID do ProdutoPedido que está sendo avaliado.
     * @param nota Nota da avaliação.
     * @param descricao Descrição opcional da avaliação.
     * @param userId ID do usuário que está realizando a avaliação.
     * @return AvaliacaoModel representando a avaliação criada.
     * @throws NoSuchElementException se o ProdutoPedido ou o usuário não forem encontrados.
     */
    @Transactional
    public AvaliacaoModel criarAvaliacao(int produtoPedidoId, int nota, String descricao, Long userId) {
        ProdutoPedidoModel produtoPedido = produtoPedidoRepository.findById(produtoPedidoId)
                .orElseThrow(() -> new NoSuchElementException("ProdutoPedido não encontrado para o ID: " + produtoPedidoId));
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado para o ID: " + userId));

        AvaliacaoModel avaliacao = new AvaliacaoModel();
        avaliacao.setProdutoPedido(produtoPedido);
        avaliacao.setUser(user);
        avaliacao.setNota(nota);
        avaliacao.setDescricao(descricao);

        return avaliacaoRepository.save(avaliacao);
    }
}
