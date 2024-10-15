package com.app.controller;

import com.app.model.UserModel;
import com.app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador responsável pela gestão das rotas relacionadas ao usuário.
 *
 * Este controlador manipula a autenticação do usuário, incluindo login via
 * formulário e OAuth2, além de gerenciar o registro de novos usuários. As rotas
 * incluem páginas de login e cadastro, bem como o processamento de credenciais
 * de autenticação e armazenamento de informações do usuário.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-05
 */
@Controller
public class UserController {

    /**
     * Serviço de usuário utilizado para lógica de negócios relacionada a
     * usuários.
     */
    private final UserService userService;

    /**
     * URL do frontend, injetada a partir das configurações do aplicativo para
     * ser usado de forma dinâmica.
     */
    @Value("${frontend.url}")
    private String frontendUrl;

    /**
     * Serviço de autenticação para processar login por formulário.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Construtor que injeta o serviço de usuário e o AuthenticationManager via
     * injeção de dependência do Spring.
     *
     * @param userService O serviço de usuário a ser injetado.
     * @param authenticationManager O AuthenticationManager a ser injetado.
     */
    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
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
     * Rota que processa o login do usuário via formulário.
     *
     * Este método autentica o usuário com base no email e senha fornecidos. Se
     * a autenticação for bem-sucedida, o usuário autenticado é armazenado na
     * sessão e o usuário é redirecionado para a URL do frontend. Em caso de
     * falha, a página de login é retornada com uma mensagem de erro.
     *
     * @param email O email fornecido pelo usuário.
     * @param password A senha fornecida pelo usuário.
     * @param model O modelo para passar dados à view.
     * @param request O objeto HttpServletRequest para manipulação da sessão.
     * @return Um redirecionamento para a URL da home page em caso de sucesso,
     * ou retorno à página de login em caso de falha.
     */
    @PostMapping("/form-process")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model, HttpServletRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserModel user = userService.getUserFromAuthentication(authentication);
            request.getSession().setAttribute("user", user);

            return "redirect:" + frontendUrl;
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Credenciais inválidas");
            return "customLogin";
        }
    }

    /**
     * Rota que processa as informações do usuário após a autenticação com
     * OAuth2.
     *
     * Este método extrai os atributos do usuário autenticado a partir do token
     * OAuth2, registra o usuário no sistema caso ainda não esteja registrado, e
     * armazena o objeto `UserModel` na sessão para uso posterior. Após o
     * processamento, o usuário é redirecionado para a URL do frontend.
     *
     * @param token O token de autenticação do usuário autenticado via OAuth2.
     * @param request O objeto HttpServletRequest para acessar a sessão e
     * armazenar o usuário autenticado.
     * @return Um redirecionamento para a URL do frontend.
     */
    @GetMapping("/google-process")
    public String processUser(OAuth2AuthenticationToken token, HttpServletRequest request) {
        String name = token.getPrincipal().getAttribute("name");
        String email = token.getPrincipal().getAttribute("email");

        userService.saveUserIfNotExists(name, email);

        UserModel authenticatedUser = new UserModel();
        authenticatedUser.setUserName(name);
        authenticatedUser.setEmailId(email);
        authenticatedUser.setRoles("ROLE_USER");

        request.getSession().setAttribute("user", authenticatedUser);

        return "redirect:" + frontendUrl;
    }

    /**
     * Rota que exibe a página de cadastro de usuário.
     *
     * Este método inicializa um novo objeto UserModel e o adiciona ao modelo,
     * que será utilizado para renderizar a página de cadastro.
     *
     * @param model O modelo para passar dados à view.
     * @return O nome da view que renderiza a página de cadastro.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "customSignin";
    }

    /**
     * Rota que processa o cadastro de um novo usuário.
     *
     * Este método recebe os dados do formulário de registro, cria um novo
     * usuário no sistema através do serviço de usuário e, em seguida,
     * redireciona para a URL da home page.
     *
     * @param user O objeto UserModel com os dados do formulário.
     * @return Um redirecionamento para a URL da home page.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserModel user) {
        userService.registerUser(user);
        return "customLogin";
    }
}
