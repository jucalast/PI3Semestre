package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.MetodoPreparo;
import com.app.service.MetodoPreparoService;

@RestController
@RequestMapping("/api/metodo-preparo")
public class MetodoPreparoController {

    @Autowired
    private MetodoPreparoService metodoPreparoService;

    @PostMapping
    public ResponseEntity<MetodoPreparo> createMetodoPreparo(@RequestBody MetodoPreparo metodoPreparo) {
        MetodoPreparo createdMetodo = metodoPreparoService.createMetodoPreparo(metodoPreparo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMetodo);
    }

    // Nova rota para buscar método de preparo pelo id do produto
    @GetMapping("/produto/{id}")
    public ResponseEntity<MetodoPreparo> buscarMetodoPreparoPorProdutoId(@PathVariable Long id) {
        MetodoPreparo metodoPreparo = metodoPreparoService.buscarMetodoPreparoPorProdutoId(id);
        return metodoPreparo != null ? ResponseEntity.ok(metodoPreparo) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
