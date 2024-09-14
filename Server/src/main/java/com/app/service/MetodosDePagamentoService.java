package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.MetodosDePagamentos;
import com.app.repository.MetodosDePagamentoRepository;

@Service
public class MetodosDePagamentoService{

    @Autowired
    private MetodosDePagamentoRepository metodosDePagamentosRepository;

    // Busca todos os métodos de pagamento
    public List<MetodosDePagamentos> getAllMetodosDePagamentos() {
        return metodosDePagamentosRepository.findAll();
    }

    // Busca um método de pagamento pelo ID
    public MetodosDePagamentos getMetodoDePagamentoById(Integer id) {
        Optional<MetodosDePagamentos> optionalMetodo = metodosDePagamentosRepository.findById(id);
        return optionalMetodo.orElse(null); // Retorna o método ou null se não for encontrado
    }

    // Cria um novo método de pagamento
    public MetodosDePagamentos createMetodoDePagamento(MetodosDePagamentos metodo) {
        return metodosDePagamentosRepository.save(metodo);
    }

    // Atualiza um método de pagamento existente
    public MetodosDePagamentos updateMetodoDePagamento(Integer id, MetodosDePagamentos metodoAtualizado) {
        Optional<MetodosDePagamentos> optionalMetodo = metodosDePagamentosRepository.findById(id);
        if (optionalMetodo.isPresent()) {
            MetodosDePagamentos metodoExistente = optionalMetodo.get();
            metodoExistente.setNome(metodoAtualizado.getNome());
            metodoExistente.setTaxa(metodoAtualizado.getTaxa());
            return metodosDePagamentosRepository.save(metodoExistente);
        } else {
            return null;
        }
    }

    // Deleta um método de pagamento pelo ID
    public boolean deleteMetodoDePagamento(Integer id) {
        Optional<MetodosDePagamentos> optionalMetodo = metodosDePagamentosRepository.findById(id);
        if (optionalMetodo.isPresent()) {
            metodosDePagamentosRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
