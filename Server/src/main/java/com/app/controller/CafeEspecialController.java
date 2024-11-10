package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.CafeEspecial;
import com.app.service.CafeEspecialService;

/**
 * Controlador para gerenciar operações relacionadas a cafés especiais.
 *
 * Este controlador fornece endpoints para criar, listar e buscar cafés
 * especiais associados a um produto específico.
 *
 * @author JoãO
 * @version 1.0
 * @since 2024-10-05
 */
@RestController
@RequestMapping("/api/cafes-especiais")
public class CafeEspecialController {

    @Autowired
    private CafeEspecialService cafeEspecialService;

    /**
     * Endpoint para criar um novo Café Especial.
     *
     * @param cafeEspecial Objeto CafeEspecial a ser criado.
     * @return O café especial recém-criado com o status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<CafeEspecial> createCafeEspecial(@RequestBody CafeEspecial cafeEspecial) {
        CafeEspecial createdCafe = cafeEspecialService.createCafeEspecial(cafeEspecial);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCafe);
    }

    /**
     * Endpoint para listar todos os cafés especiais.
     *
     * @return A lista de todos os cafés especiais.
     */
    @GetMapping
    public List<CafeEspecial> listarCafesEspeciais() {
        return cafeEspecialService.listarCafesEspeciais();
    }

    /**
     * Endpoint para buscar cafés especiais associados a um produto específico.
     *
     * @param id O ID do produto.
     * @return A lista de cafés especiais associados ao produto, ou uma resposta
     * 404 caso nenhum café especial seja encontrado para o produto.
     */
    @GetMapping("/produto/{id}")
    public ResponseEntity<List<CafeEspecial>> getCafesEspeciaisPorProduto(@PathVariable Long id) {
        List<CafeEspecial> cafesEspeciais = cafeEspecialService.findByProdutoId(id);
        return ResponseEntity.ok(cafesEspeciais);
    }
}
