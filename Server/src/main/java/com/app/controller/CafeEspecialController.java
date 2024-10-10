package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.app.model.CafeEspecial;
import com.app.service.CafeEspecialService;

@RestController
@RequestMapping("/api/cafes-especiais")
public class CafeEspecialController {

    @Autowired
    private CafeEspecialService cafeEspecialService; // Keep this declaration

    @PostMapping
    public ResponseEntity<CafeEspecial> createCafeEspecial(@RequestBody CafeEspecial cafeEspecial) {
        CafeEspecial createdCafe = cafeEspecialService.createCafeEspecial(cafeEspecial);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCafe);
    }

    @GetMapping
    public List<CafeEspecial> listarCafesEspeciais() {
        return cafeEspecialService.listarCafesEspeciais();
    }

    // Rota para buscar café especial pelo ID do produto
    @GetMapping("/produto/{id}")
    public ResponseEntity<List<CafeEspecial>> getCafesEspeciaisPorProduto(@PathVariable Long id) {
        List<CafeEspecial> cafesEspeciais = cafeEspecialService.findByProdutoId(id);
        return ResponseEntity.ok(cafesEspeciais);
    }
}
