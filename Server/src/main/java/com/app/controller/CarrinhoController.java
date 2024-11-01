package com.app.controller;

import com.app.model.Carrinho;
import com.app.repository.CarrinhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas ao Carrinho.
 */
@RestController
@AllArgsConstructor
public class CarrinhoController {
    
    private final CarrinhoRepository repository;

    /**
     * Retorna uma lista de todos os carrinhos.
     * 
     * @return lista de carrinhos.
     */
    @GetMapping("api/carrinho")
    public List<Carrinho> getAllCarrinhos() {
        return repository.findAll();
    }

    /**
     * Retorna um carrinho específico pelo ID.
     * 
     * @param id ID do carrinho.
     * @return o carrinho correspondente ao ID.
     */
    @GetMapping("api/carrinho/{id}")
    public Carrinho getCarrinhoById(@PathVariable long id) {
        return repository.getOne(id);
    }

    /**
     * Salva um novo carrinho.
     * 
     * @param carrinho o carrinho a ser salvo.
     * @return o carrinho salvo.
     */
    @PostMapping("/api/carrinho")
    public Carrinho saveCarrinho(@RequestBody Carrinho carrinho) {
        return repository.save(carrinho);
    }

    /**
     * Deleta um carrinho pelo ID.
     * 
     * @param id ID do carrinho a ser deletado.
     */
    @DeleteMapping("api/carrinho/{id}")
    public void deletaCarrinho(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
