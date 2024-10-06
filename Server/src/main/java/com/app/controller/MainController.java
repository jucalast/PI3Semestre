package com.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controlador principal da aplicação.
 *
 * Este controlador fornece rotas básicas para a aplicação, incluindo uma
 * página inicial de boas-vindas e uma rota para acessar informações do
 * usuário autenticado.
 *
 * A anotação @RestController indica que a classe é um componente do Spring
 * que trata requisições web e retorna respostas diretamente, geralmente em
 * formato JSON.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-05
 */
@RestController
public class MainController {

    /**
     * Rota que exibe uma mensagem de boas-vindas.
     *
     * @return Uma mensagem de boas-vindas.
     */
    @RequestMapping("/")
    public String home() {
        return "Welcome!";
    }

    /**
     * Rota que retorna as informações do usuário autenticado.
     *
     * Este método recebe o objeto Principal, que contém as informações do
     * usuário atualmente autenticado, e as retorna.
     *
     * @param user O objeto Principal que representa o usuário autenticado.
     * @return O objeto Principal com as informações do usuário.
     */
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

}
