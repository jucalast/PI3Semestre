package com.app.controller;

import com.app.model.Carrinho;
import com.app.model.Produto;
import com.app.model.UserModel;
import com.app.repository.CarrinhoRepository;
import com.app.service.CarrinhoService;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@RestController
@RequestMapping("api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Transactional
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<?> addProductsOnUserCart(@PathVariable Long userId, @PathVariable Long productId){
        try{
            boolean insertItem = carrinhoService.insertProductOnUserCart(userId, productId);
            if(insertItem){
                return ResponseEntity.ok("Produto adicionado ao carrinho");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro");
            }
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar adicionar produto ao carrinho" + e.getMessage());
        }
    }

    @GetMapping("/{id_user}")
    public ResponseEntity<List<Map<String, Object>>> listProductsOnUserCart(@PathVariable Long id_user) {
        try {
            List<Map<String, Object>> carrinhoItems = carrinhoService.getProductsOnUserCart(id_user);
            if (carrinhoItems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(carrinhoItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Transactional
    @DeleteMapping("/{id_user}/{productId}")
    public ResponseEntity<?> removeProductOnUserCart(@PathVariable Long id_user, @PathVariable Long productId){
        try{
            boolean isRemoved = carrinhoService.deleteProductOnUserCart(id_user, productId);
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

