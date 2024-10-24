package com.app.controller;

import com.app.model.Carrinho;
import com.app.repository.CarrinhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CarrinhoController {

    CarrinhoRepository repository;

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
    }
}
