package com.app.controller;

import com.app.model.CafeEspecial;
import com.app.service.CafeEspecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cafes")
public class CafeEspecialController {

    @Autowired
    private CafeEspecialService cafeEspecialService;

    /**
     * Endpoint para criar um novo café especial.
     *
     * @param cafeEspecial O café especial a ser criado.
     * @return Resposta com o café especial criado.
     */
    @PostMapping
    public ResponseEntity<CafeEspecial> createCafeEspecial(@RequestBody CafeEspecial cafeEspecial) {
        System.out.println("Recebido Café Especial: " + cafeEspecial);
        CafeEspecial createdCafeEspecial = cafeEspecialService.createCafeEspecial(cafeEspecial);
        return ResponseEntity.ok(createdCafeEspecial);
    }


    /**
     * Endpoint para obter um café especial pelo ID.
     *
     * @param id ID do café especial.
     * @return Resposta com o café especial encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CafeEspecial> getCafeEspecialById(@PathVariable Long id) {
        Optional<CafeEspecial> cafeEspecial = cafeEspecialService.getCafeEspecialById(id);
        return cafeEspecial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
