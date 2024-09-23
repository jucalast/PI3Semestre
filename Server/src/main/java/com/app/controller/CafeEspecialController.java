package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.CafeEspecial;
import com.app.service.CafeEspecialService;

@RestController
@RequestMapping("/api/cafes-especiais")
public class CafeEspecialController {

    @Autowired
    private CafeEspecialService cafeEspecialService;

    @PostMapping
    public ResponseEntity<CafeEspecial> createCafeEspecial(@RequestBody CafeEspecial cafeEspecial) {
        CafeEspecial createdCafe = cafeEspecialService.createCafeEspecial(cafeEspecial);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCafe);
    }
}
