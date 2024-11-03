package com.app.controller;

import com.app.model.Carrinho;
import com.app.model.UserModel;
import jakarta.transaction.Transactional;
import com.app.service.CarrinhoService;
import org.apache.coyote.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@RestController
@RequestMapping("api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Transactional
    @PostMapping("/{productId}")
    public ResponseEntity<?> addProductsOnUserCart(HttpServletRequest request, @PathVariable Long productId){
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        if(authenticatedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: Usuário não autenticado.");
        }
        try{
            boolean insertItem = carrinhoService.insertProductOnUserCart(authenticatedUser.getId(), productId);
            if(insertItem){
                return ResponseEntity.ok("Sucesso: Produto adicionado ao carrinho");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro");
            }
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar adicionar produto ao carrinho" + e.getMessage());
        }
    }

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
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar carregar o carrinho.");
            }
        }
    }

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
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro");
        }
    }
}

