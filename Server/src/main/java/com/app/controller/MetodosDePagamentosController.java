package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.MetodosDePagamentos;
import com.app.service.MetodosDePagamentoService;

@RestController
@RequestMapping("/api/metodos-pagamentos")
public class MetodosDePagamentosController {

    @Autowired
    private MetodosDePagamentoService metodosDePagamentosService;

    // Retorna todos os métodos de pagamento
    @GetMapping
    public ResponseEntity<List<MetodosDePagamentos>> getAllMetodosDePagamentos() {
        try {
            List<MetodosDePagamentos> metodos = metodosDePagamentosService.getAllMetodosDePagamentos();
            return new ResponseEntity<>(metodos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retorna um método de pagamento específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<MetodosDePagamentos> getMetodoDePagamentoById(@PathVariable Integer id) {
        try {
            MetodosDePagamentos metodo = metodosDePagamentosService.getMetodoDePagamentoById(id);
            if (metodo != null) {
                return new ResponseEntity<>(metodo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cria um novo método de pagamento
    @PostMapping
    public ResponseEntity<MetodosDePagamentos> createMetodoDePagamento(@RequestBody MetodosDePagamentos metodo) {
        try {
            MetodosDePagamentos novoMetodo = metodosDePagamentosService.createMetodoDePagamento(metodo);
            return new ResponseEntity<>(novoMetodo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Atualiza um método de pagamento existente
    @PutMapping("/{id}")
    public ResponseEntity<MetodosDePagamentos> updateMetodoDePagamento(
            @PathVariable Integer id, @RequestBody MetodosDePagamentos metodo) {
        try {
            MetodosDePagamentos metodoAtualizado = metodosDePagamentosService.updateMetodoDePagamento(id, metodo);
            if (metodoAtualizado != null) {
                return new ResponseEntity<>(metodoAtualizado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Deleta um método de pagamento pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetodoDePagamento(@PathVariable Integer id) {
        try {
            boolean isDeleted = metodosDePagamentosService.deleteMetodoDePagamento(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
