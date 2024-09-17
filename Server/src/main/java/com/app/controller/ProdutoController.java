package com.app.controller;

import com.app.model.Produto;
import com.app.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
@Validated
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * Endpoint para criar um Produto associado a um Café Especial ou a um
     * Método de Preparo.
     *
     * @param produto o Produto a ser criado
     * @return resposta com o Produto criado
     */
    @PostMapping
    public ResponseEntity<Produto> criarProduto(
            @Valid @RequestBody Produto produto,
            @RequestParam(required = false) Long cafeEspecialId,
            @RequestParam(required = false) Long metodoPreparoId) {

        Produto novoProduto;
        try {
            novoProduto = produtoService.criarProduto(produto, cafeEspecialId, metodoPreparoId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Ajuste conforme necessário
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

}
