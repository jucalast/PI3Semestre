package com.app.service;

import com.app.model.pagamento.CartaoModel;
import com.app.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Serviço que oferece operações para manipulação de cartões de crédito.
 */
@Service
public class CartaoService {

    /**
     * Repositório para acesso aos dados de cartões.
     */
    @Autowired
    private CartaoRepository cartaoRepository;

    /**
     * Busca e retorna todos os cartões associados ao ID de um usuário.
     *
     * @param userId ID do usuário para o qual buscar os cartões.
     * @return Lista de CartaoModel pertencentes ao usuário especificado.
     */
    public List<CartaoModel> findCartoesByUserId(Long userId) {
        List<CartaoModel> cartoes = cartaoRepository.findByUserId(userId);
        cartoes.forEach(cartao -> System.out.println("Número do cartão: " + cartao.getNumeroCartao()));
        return cartoes;
    }

    /**
     * Salva um novo cartão no repositório.
     *
     * @param cartaoModel O modelo do cartão a ser salvo.
     * @return O modelo do cartão salvo.
     */
    public CartaoModel saveCartao(CartaoModel cartaoModel) {
        return cartaoRepository.save(cartaoModel);
    }
}
