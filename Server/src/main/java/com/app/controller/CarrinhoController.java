package com.app.controller;

import com.app.model.Carrinho;
import com.app.model.UserModel;
import jakarta.transaction.Transactional;
import com.app.service.CarrinhoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador responsável pela gestão das rotas relacionadas ao carrinho.
 *
 * Este controlador valida a autenticação da sessão do usuário e gerenciamento
 * da das rotas de manipulação do carrinho.
 *
 * @autor Ricardo L. Ferreira
 */
@RestController
@RequestMapping("api/carrinho")
public class CarrinhoController {

    /**
     * Serviço de carrinho utilizado para lógica de negócios relacionada ao carrinho
     */
    @Autowired
    private CarrinhoService carrinhoService;

    /**
     * Rota que processa a adição de um novo item no carrinho.
     *
     * @param request A partir da interface HttpServeletRequest acessamos informações
     *                detalhadas de uma solicitação HTTP, nesse caso a sessão do usuário
     *                para autenticação.
     * @param productId O id do produto.
     * @return O retorno é um status HTTP representando se a solicitação foi concluída com sucesso ou não.
     */
    @Transactional
    @PostMapping("/{productId}")
    public ResponseEntity<?> addProductsOnUserCart(HttpServletRequest request, @PathVariable Long productId){
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if(authenticatedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }
        try{
            boolean insertItem = carrinhoService.insertProductOnUserCart(authenticatedUser.getId(), productId);
            if(insertItem){
                return ResponseEntity.ok("Sucesso: Produto adicionado ao carrinho");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na requisição");
            }
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: Algo de errado aconteceu. " + e.getMessage());
        }
    }

    /**
     * Rota responsável por processar a coleta dos itens existentes no carrinho
     *
     * @param request A partir da interface HttpServeletRequest acessamos informações
     *                detalhadas de uma solicitação HTTP, nesse caso a sessão do usuário
     *                para autenticação.
     * @return O retorno é um status HTTP representando se a solicitação foi concluída com sucesso ou não.
     */
    @GetMapping("/")
    public ResponseEntity<?> listProductsOnUserCart(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        } else {
            try {
                List<Map<String, Object>> carrinhoItems = carrinhoService.getProductsOnUserCart(authenticatedUser.getId());
                if (carrinhoItems.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
                return ResponseEntity.ok(carrinhoItems);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: Algo de errado aconteceu. " + e.getMessage());
            }
        }
    }

    /**
     * Rota que processa a atualização da quantidade unitária de cada item presente no carrinho
     * @param request A partir da interface HttpServeletRequest acessamos informações
     *                detalhadas de uma solicitação HTTP, nesse caso a sessão do usuário
     *                para autenticação.
     * @param productId O id do produto.
     * @param quantity A quantidade a ser atualizada do item no carrinho.
     * @return O retorno é um status HTTP representando se a solicitação foi concluída com sucesso ou não.
     */
    @Transactional
    @PutMapping("/{productId}/{quantity}")
    public ResponseEntity<?> postQuantityItemOnUserCart(HttpServletRequest request, @PathVariable Long productId, @PathVariable int quantity){
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if(authenticatedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        } else {
            try{
                boolean updateQntty = carrinhoService.updateQuantityOnUserCart(authenticatedUser.getId(), productId, quantity);
                if(updateQntty){
                    return ResponseEntity.ok().body("Quantidade atualizada");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na requição");
                }
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: Algo de errado aconteceu. " + e.getMessage());
            }
        }
    }

    /**
     * Rota que processa a remoção de itens do carrinho
     *
     * @param request A partir da interface HttpServeletRequest acessamos informações
     *                detalhadas de uma solicitação HTTP, nesse caso a sessão do usuário
     *                para autenticação.
     * @param productId O id do produto.
     * @return O retorno é um status HTTP representando se a solicitação foi concluída com sucesso ou não.
     */
    @Transactional
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeProductOnUserCart(HttpServletRequest request, @PathVariable Long productId){
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if(authenticatedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }
        try{
            boolean isRemoved = carrinhoService.deleteProductOnUserCart(authenticatedUser.getId(), productId);
            if (isRemoved){
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Produto não encontrado");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: Algo de errado aconteceu. " + e.getMessage());
        }
    }
}

