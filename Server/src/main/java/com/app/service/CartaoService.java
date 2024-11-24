package com.app.service;

import com.app.model.CartaoModel;
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
        return cartaoRepository.findByUserId(userId);
    }
}
