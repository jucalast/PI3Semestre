package com.app.controller;

import com.app.model.CafeEspecial;
import com.app.service.CafeEspecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cafes-especiais")
public class CafeEspecialController {

    @Autowired
    private CafeEspecialService cafeEspecialService;

    @GetMapping("/{id}")
    public ResponseEntity<CafeEspecial> buscarPorId(@PathVariable Long id) {
        return cafeEspecialService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CafeEspecial criarCafeEspecial(@RequestBody CafeEspecial cafeEspecial) {
        return cafeEspecialService.salvarCafeEspecial(cafeEspecial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CafeEspecial> atualizarCafeEspecial(@PathVariable Long id, @RequestBody CafeEspecial cafeEspecialAtualizado) {
        return ResponseEntity.ok(cafeEspecialService.atualizarCafeEspecial(id, cafeEspecialAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCafeEspecial(@PathVariable Long id) {
        cafeEspecialService.deletarCafeEspecial(id);
        return ResponseEntity.noContent().build();
    }
}