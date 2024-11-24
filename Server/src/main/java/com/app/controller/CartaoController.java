package com.app.controller;

import com.app.model.CartaoModel;
import com.app.model.UserModel;
import com.app.service.CartaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlador responsável pela gestão das requisições HTTP relacionadas aos cartões de crédito.
 */
@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    /**
     * Serviço de gerenciamento de cartões.
     */
    @Autowired
    private CartaoService cartaoService;

    /**
     * Recupera uma lista de todos os cartões associados ao usuário autenticado.
     *
     * @param request Objeto HttpServletRequest que contém a sessão do usuário.
     * @return Lista de CartaoModel pertencentes ao usuário autenticado.
     */
    @GetMapping("/meus-cartoes")
    public List<CartaoModel> getMyCartoes(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        Long userId = authenticatedUser.getId();
        return cartaoService.findCartoesByUserId(userId);
    }
}
