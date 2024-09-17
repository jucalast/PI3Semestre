package com.app.controller;

import com.app.model.MetodoPreparo;
import com.app.service.MetodoPreparoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gerenciar métodos de preparo de café.
 */
@RestController
@RequestMapping("/api/metodos-preparo")
public class MetodoPreparoController {

    @Autowired
    private MetodoPreparoService metodoPreparoService;

    /**
     * Cria um novo método de preparo de café.
     *
     * @param metodoPreparo o método de preparo a ser criado
     * @return resposta com o método de preparo criado e status HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<MetodoPreparo> criarMetodoPreparo(@RequestBody MetodoPreparo metodoPreparo) {
        MetodoPreparo criado = metodoPreparoService.criarMetodoPreparo(metodoPreparo);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }
}
