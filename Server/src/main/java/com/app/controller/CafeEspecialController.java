package com.app.controller;

import com.app.model.CafeEspecial;
import com.app.service.CafeEspecialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cafes-especiais")
@Validated
public class CafeEspecialController {

    @Autowired
    private CafeEspecialService cafeEspecialService;

    /**
     * Endpoint para criar um Café Especial.
     *
     * @param cafeEspecial o Café Especial a ser criado
     * @return resposta com o Café Especial criado
     */
    @PostMapping
    public ResponseEntity<CafeEspecial> criarCafeEspecial(@Valid @RequestBody CafeEspecial cafeEspecial) {
        CafeEspecial novoCafeEspecial;
        try {
            novoCafeEspecial = cafeEspecialService.criarCafeEspecial(cafeEspecial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Ajuste conforme necessário
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCafeEspecial);
    }
}
