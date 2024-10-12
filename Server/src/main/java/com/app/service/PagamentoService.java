package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Pagamento;
import com.app.repository.PagamentoRepository;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento salvarPagamento(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento obterPagamentoPorId(Integer id) {
        return pagamentoRepository.findById(id).orElse(null);
    }

    public Pagamento atualizarPagamento(Integer id, Pagamento pagamento) {
        // Verifica se o pagamento existe antes de atualizar
        if (pagamentoRepository.existsById(id)) {
            pagamento.setId(id); // define o ID no objeto Pagamento
            return pagamentoRepository.save(pagamento);
        }
        return null; // ou lançar uma exceção apropriada
    }

    public boolean removerPagamento(Integer id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
            return true;
        }
        return false; // ou lançar uma exceção apropriada
    }
}
