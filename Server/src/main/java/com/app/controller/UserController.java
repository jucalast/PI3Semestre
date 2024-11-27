package com.app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.model.UserModel;
import com.app.model.AddressModel;
import com.app.service.UserService;
import com.app.service.AddressService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
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
     * Serviço de endereço utilizado para lógica de negócios relacionada a
     * endereços.
     */
    private final AddressService addressService;

    /**
     * Construtor que injeta o serviço de usuário, o AuthenticationManager e o
     * AddressService via injeção de dependência do Spring.
     *
     * @param userService O serviço de usuário a ser injetado.
     * @param authenticationManager O AuthenticationManager a ser injetado.
     * @param addressService O AddressService a ser injetado.
     */
    public UserController(UserService userService, AuthenticationManager authenticationManager, AddressService addressService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.addressService = addressService;
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
    @PostMapping("/login/form-process")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            log.info("Usuário autenticado: {}", authentication.getName());
            log.info("Authorities do usuário: {}", authentication.getAuthorities());

            UserModel user = userService.getUserFromAuthentication(authentication);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("userId", user.getId());

            return ResponseEntity.ok().body(Map.of("message", "Login bem-sucedido", "userId", user.getId()));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciais inválidas"));
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
    @GetMapping("/login/google-process")
    public String processUser(OAuth2AuthenticationToken token, HttpServletRequest request) {
        String name = token.getPrincipal().getAttribute("name");
        String email = token.getPrincipal().getAttribute("email");

        UserModel user = userService.saveUserIfNotExists(name, email);

        UserModel authenticatedUser = new UserModel();
        authenticatedUser.setId(user.getId());
        authenticatedUser.setUserName(name);
        authenticatedUser.setEmailId(email);
        authenticatedUser.setRoles("ROLE_USER");

        request.getSession().setAttribute("user", authenticatedUser);
        request.getSession().setAttribute("userId", authenticatedUser.getId());

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
    public ResponseEntity<String> registerUser(@ModelAttribute UserModel user) {
        userService.registerUser(user);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    /**
     * Rota protegida que só pode ser acessada por administradores.
     *
     * @return Uma mensagem informando que o acesso foi bem-sucedido.
     */
    @GetMapping("/protected/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> testAdminAccess(HttpServletRequest request) {

        return ResponseEntity.ok("Acesso concedido: Você está acessando uma rota protegida como administrador!");
    }

    /**
     * Rota que verifica se o usuário está autenticado.
     *
     * @return Uma mensagem informando se o usuário está autenticado ou não.
     */
    @GetMapping("/check-auth")
    public ResponseEntity<Map<String, Object>> checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> response = new HashMap<>();

        if (authentication != null && authentication.isAuthenticated()) {

            response.put("authenticated", true);
            response.put("email", authentication.getName());

            if (authentication.getPrincipal() instanceof OAuth2User) {

                OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

                String photoUrl = oauthUser.getAttribute("picture");
                response.put("photoUrl", photoUrl);
                String firstName = oauthUser.getAttribute("given_name");
                response.put("username", firstName);
                List<String> oauthRoles = Collections.singletonList("ROLE_USER");
                response.put("roles", oauthRoles);

            } else if (authentication.getPrincipal() instanceof UserDetails) {

                response.put("username", userService.getUserFromAuthentication(authentication).getUserName());
                response.put("photoUrl", userService.getUserFromAuthentication(authentication).getProfilePic());
                List<String> formLoginRoles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                response.put("roles", formLoginRoles);

            }

            return ResponseEntity.ok(response);
        }

        response.put("authenticated", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PutMapping("/api/profile")
    public ResponseEntity<?> updateUserProfile(HttpServletRequest request, @RequestBody Map<String, Object> updates) {

        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
    
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }
    
        try {

            UserModel updatedUser = userService.updateUser(authenticatedUser.getId(), updates);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o perfil.");
        }
    }
    
}