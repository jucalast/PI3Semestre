package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.model.UserModel;
import com.app.service.CarrinhoService;

import jakarta.servlet.http.HttpServletRequest;

public class CarrinhoVendaController {

  /**
   * Este metodo recebe os itens do carrinho armazena e chama a função de cadastro de venda
   */
  /**UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user"); */
@PostMapping("/conversao")
  public ResponseEntity<?> moverParaPedido(HttpServletRequest request, @RequestParam Long productId) {
    UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
    Long userId = authenticatedUser.getId();
    CarrinhoService carrinho = new CarrinhoService();
    String result[] = carrinho.getProductsOnUserCart(userId);
    
    }
  }
  
