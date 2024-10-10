package com.app.controller;

import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador responsável pela gestão das rotas relacionadas ao usuário.
 *
 * Este controlador lida com a autenticação de usuários via OAuth2 e a exibição de perfis de usuário.
 *
 * A anotação @Controller indica que esta classe é um componente do Spring que
 * trata as requisições web e retorna as respostas apropriadas.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-05
 */
@Controller
public class UserController {

    /**
     * Serviço de usuário utilizado para lógica de negócios relacionada a usuários.
     */
    private final UserService userService;

    /**
     * Construtor que injeta o serviço de usuário via autowired (injeção de dependência do Spring).
     *
     * @param userService O serviço de usuário a ser injetado.
     */
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Rota que exibe o perfil do usuário autenticado.
     *
     * Este método obtém informações do usuário a partir do token de autenticação
     * e as adiciona ao modelo para exibição na página de perfil.
     *
     * @param token O token de autenticação do usuário autenticado.
     * @param model O modelo para passar dados à view.
     * @return O nome da view que renderiza o perfil do usuário.
     */
    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model) {
        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        model.addAttribute("picture", token.getPrincipal().getAttribute("picture"));
        return "userProfile";
    }

    /**
     * Rota que exibe a página de login personalizada.
     *
     * @return O nome da view que renderiza a página de login.
     */
    @GetMapping("/login")
    public String login() {
        return "customLogin";
    }

    /**
     * Rota que processa as informações do usuário após a autenticação com OAuth2.
     *
     * Este método salva o usuário no banco de dados se ele não existir e redireciona
     * para o perfil do usuário.
     *
     * @param token O token de autenticação do usuário autenticado.
     * @param model O modelo para passar dados à view.
     * @return Um redirecionamento para a rota de perfil do usuário.
     */
    @GetMapping("/process-user")
    public String processUser(OAuth2AuthenticationToken token, Model model) {
        String name = token.getPrincipal().getAttribute("name");
        String email = token.getPrincipal().getAttribute("email");

        userService.saveUserIfNotExists(name, email);

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "redirect:/profile";
    }
}
