package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
