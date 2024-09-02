package com.seu_pacote.controller;

import com.seu_pacote.model.CafeEspecial;
import com.seu_pacote.service.CafeEspecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cafe-especial")
public class CafeEspecialController {

    @Autowired
    private CafeEspecialService cafeEspecialService;

    @PostMapping
    public ResponseEntity<CafeEspecial> createCafeEspecial(@RequestBody CafeEspecial cafeEspecial) {
        CafeEspecial savedCafeEspecial = cafeEspecialService.saveCafeEspecial(cafeEspecial);
        return ResponseEntity.ok(savedCafeEspecial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CafeEspecial> getCafeEspecialById(@PathVariable Long id) {
        Optional<CafeEspecial> cafeEspecial = cafeEspecialService.getCafeEspecialById(id);
        return cafeEspecial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafeEspecial(@PathVariable Long id) {
        cafeEspecialService.deleteCafeEspecial(id);
        return ResponseEntity.noContent().build();
    }

    // Outros endpoints podem ser adicionados aqui
}
