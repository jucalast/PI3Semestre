package com.app.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.PagCartaoModel;
import com.app.model.PagamentoModel;
import com.app.repository.PagCartaoRepository;
import com.app.repository.PagamentoRepository;

/**
 * Serviço que contém as regras de negócios e operações de persistência
 * relacionadas à entidade 'pagamentos'. Este serviço faz uso dos repositórios
 * PagamentoRepository e PagCartaoRepository para acessar o banco de dados e manipular
 * as informações de pagamento e pagamento por cartão.
 * 
 * A anotação @Service indica que esta classe é um serviço Spring,
 * permitindo a injeção de dependência e a implementação de lógica de negócios.
 * 
 * @author Kairo Chácara
 * @version 1.1
 * @since 2024-10-21
 */
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagCartaoRepository pagCartaoRepository;

    /**
     * Método para buscar um pagamento pelo seu ID.
     * 
     * @param id ID do pagamento a ser encontrado.
     * @return Um Optional contendo o pagamento, ou vazio se não for encontrado.
     */
    public Optional<PagamentoModel> buscarPagamentoPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    /**
     * Método para salvar ou atualizar um pagamento.
     * 
     * @param pagamento Objeto PagamentoModel contendo as informações do pagamento.
     * @return O pagamento salvo ou atualizado.
     */
    public PagamentoModel salvarPagamento(PagamentoModel pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    /**
     * Método para excluir um pagamento pelo seu ID.
     * 
     * @param id ID do pagamento a ser excluído.
     */
    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }

    /**
     * Método para buscar um pagamento pelo transactionId.
     * 
     * @param transactionId Transaction ID do pagamento.
     * @return Um Optional contendo o pagamento, ou vazio se não for encontrado.
     */
    public Optional<PagamentoModel> buscarPagamentoPorTransactionId(String transactionId) {
        return Optional.ofNullable(pagamentoRepository.findByTransactionId(transactionId));
    }

    /**
     * Método para aplicar um desconto ao pagamento.
     * Atualiza o campo 'totalComDesconto' do pagamento.
     * 
     * @param pagamento O pagamento no qual o desconto será aplicado.
     * @param desconto Valor do desconto a ser aplicado.
     * @return O pagamento com o total atualizado após o desconto.
     */
    public PagamentoModel aplicarDesconto(PagamentoModel pagamento, BigDecimal desconto) {
        BigDecimal totalComDesconto = pagamento.getTotal().subtract(desconto);
        pagamento.setTotalComDesconto(totalComDesconto);
        return pagamentoRepository.save(pagamento);
    }

    /**
     * Método para salvar os dados de um pagamento por cartão.
     * 
     * @param pagCartao Objeto PagCartaoModel contendo as informações do cartão.
     * @return O pagamento por cartão salvo.
     */
    public PagCartaoModel salvarPagamentoCartao(PagCartaoModel pagCartao) {
        return pagCartaoRepository.save(pagCartao);
    }
}
