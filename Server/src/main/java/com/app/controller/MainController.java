package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador principal da aplicação.
 *
 * Este controlador gerencia as rotas principais da aplicação, incluindo
 * a página inicial e a recuperação das informações do usuário autenticado.
 * Ele permite a interação com a aplicação tanto para usuários autenticados via
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
     *
     * Este método verifica se o usuário está autenticado, e se sim, coleta
     * as informações relevantes do usuário com base no tipo de autenticação.
     * Se a autenticação foi realizada via OAuth2, as informações são extraídas
     * do token de autenticação. Caso contrário, os dados são recuperados da
     * sessão do usuário autenticado via login por formulário.
     *
     * @param request O objeto HttpServletRequest, utilizado para acessar a sessão do usuário.
     * @param user O objeto Principal que representa o usuário autenticado, que pode ser
     *             um token OAuth2 ou um usuário autenticado por formulário.
     * @return Um ResponseEntity contendo um mapa com as informações do usuário em formato JSON
     *         ou uma resposta de erro 401 (Unauthorized) se o usuário não estiver autenticado.
     */
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> userInfo(HttpServletRequest request, Principal user) {
        Map<String, Object> userAttributes = new HashMap<>();

        if (user != null) {
            // Verifica se o usuário autenticado é um token OAuth2
            if (user instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) user;
                userAttributes.put("name", oauthToken.getPrincipal().getAttribute("name"));
                userAttributes.put("email", oauthToken.getPrincipal().getAttribute("email"));
                userAttributes.put("picture", oauthToken.getPrincipal().getAttribute("picture"));
            } else {
                // Se não for um token OAuth2, tenta obter o usuário da sessão
                UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
                if (authenticatedUser != null) {
                    userAttributes.put("name", authenticatedUser.getUserName());
                    userAttributes.put("email", authenticatedUser.getEmailId());
                    userAttributes.put("phone", authenticatedUser.getMobileNumber());
                }
            }

            return ResponseEntity.ok(userAttributes);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
