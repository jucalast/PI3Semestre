package com.app.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador principal da aplicação.
 *
 * Este controlador gerencia as rotas principais da aplicação, incluindo a
 * página inicial e a recuperação das informações do usuário autenticado. Ele
 * permite a interação com a aplicação tanto para usuários autenticados via
 * OAuth2 quanto para aqueles que utilizam o login por formulário.
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
     * @return Uma mensagem de boas-vindas como uma String.
     */
    @RequestMapping("/")
    public String home() {
        return "Welcome!";
    }

    /**
     * Rota que retorna as informações do usuário autenticado.
     * <p>
     * Este método verifica se o usuário está autenticado, recuperando as
     * informações diretamente da sessão. Caso o usuário tenha sido autenticado
     * via OAuth2 ou login por formulário, seus dados (id, nome, e-mail e telefone)
     * são coletados e retornados em formato JSON. Se o usuário não estiver
     * autenticado, a resposta será um erro 401 (Unauthorized).
     * <p>
     * -> Este método é útil para entender como funciona a obtenção de informações
     * por meio de uma sessão de usuário. <-
     * <p>
     * @param request O objeto HttpServletRequest utilizado para acessar a
     * sessão do usuário.
     * @param user O objeto Principal que representa o usuário autenticado.
     * @return Um ResponseEntity contendo um mapa com as informações do usuário
     * em formato JSON, ou uma resposta de erro 401 se o usuário não estiver
     * autenticado.
     */
    @GetMapping("/login/user-info")
    public ResponseEntity<Map<String, Object>> userInfo(HttpServletRequest request, Principal user) {
        Map<String, Object> userAttributes = new HashMap<>();

        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");

        if (authenticatedUser != null) {
            userAttributes.put("id", authenticatedUser.getId()); 
            userAttributes.put("role", authenticatedUser.getRoles());
            userAttributes.put("name", authenticatedUser.getUserName());
            userAttributes.put("email", authenticatedUser.getEmailId());
            userAttributes.put("phone", authenticatedUser.getMobileNumber());
            return ResponseEntity.ok(userAttributes);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Rota que verifica se o usuário está autenticado.
     *
     * @param request O objeto HttpServletRequest utilizado para acessar a
     * sessão do usuário.
     * @return Um ResponseEntity com status 200 se o usuário estiver
     * autenticado, ou 401 se não estiver.
     */
    @GetMapping("/login/is-authenticated")
    public ResponseEntity<Void> isAuthenticated(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        
        if (authenticatedUser != null) {
            return ResponseEntity.ok().build(); 
        } else {
            return ResponseEntity.status(401).build(); 
        }
    }

    

}
