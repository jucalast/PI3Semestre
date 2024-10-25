package com.app.controller;

import com.app.model.Carrinho;
import com.app.repository.CarrinhoRepository;
import com.app.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    CarrinhoRepository repository;

    @Autowired
    private CarrinhoService carrinhoService;

    /**
     * Rota para criar uma nova "linha" na tabela carrinho
     */
    @PostMapping ResponseEntity<Carrinho> createNewRowCart(@RequestBody Carrinho carrinho){
        Carrinho savedCartItem = carrinhoService.saveCartItem(carrinho);
        return ResponseEntity.ok(savedCartItem);
    }

    @GetMapping
    public List<Carrinho> listAllRowsOnCart(){
        return carrinhoService.getAllCartItems();
    }
    /*
    @GetMapping("api/carrinho")
    public List<com.app.model.Carrinho> getAllCarrinhos(){
        return repository.findAll();
    }

    @GetMapping("api/carrinho/{id}")
    public Carrinho getCarrinhoById(@PathVariable long id){
        return repository.getOne(id);
    }

    @PostMapping("/api/carrinho")
    public Carrinho saveCarrinho(@RequestBody Carrinho carrinho){
        return repository.save(carrinho);
    }

    @DeleteMapping("api/carrinho/{id}")
    public void deletaCarrinho(@PathVariable Long id){
        repository.deleteById(id);
    }*/
}
