package com.app.service;

import com.app.model.PagCartaoModel;
import com.app.model.PagamentoModel;
import com.app.DTO.PagamentoCompletoDTO;
import com.app.repository.PagCartaoRepository;
import com.app.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
private MetodoPagamentoService metodoPagamentoService;
private PagCartaoRepository pagCartaoRepository;

    // Cria ou atualiza um pagamento
    public PagamentoModel salvarPagamento(PagamentoModel pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    // Busca um pagamento pelo ID
    public Optional<PagamentoModel> buscarPagamentoPorId(Integer id) {
        return pagamentoRepository.findById(id);
    }

    // Lista todos os pagamentos
    public List<PagamentoModel> listarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    // Lista pagamentos por status
    public List<PagamentoModel> listarPagamentosPorStatus(PagamentoModel.StatusPagamento statusPagamento) {
        return pagamentoRepository.findByStatusPagamento(statusPagamento);
    }

    // Lista pagamentos por ID do pedido
    public List<PagamentoModel> listarPagamentosPorPedidoId(Integer pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId);
    }

    // Lista pagamentos por ID do cupom
    public List<PagamentoModel> listarPagamentosPorCupomId(Integer cupomId) {
        return pagamentoRepository.findByCupomId(cupomId);
    }

    // Exclui um pagamento pelo ID
    public void excluirPagamento(Integer id) {
        pagamentoRepository.deleteById(id);
    }
    @Transactional
public PagamentoModel criarPagamentoCompleto(PagamentoCompletoDTO pagamentoDTO) {
    PagamentoModel pagamento = new PagamentoModel();
    pagamento.setMetodoPagamentoId(pagamentoDTO.getMetodoPagamentoId());
    pagamento.setDataHora(pagamentoDTO.getDataHora());
    pagamento.setTotal(pagamentoDTO.getTotal());
    pagamento.setTotalComDesconto(pagamentoDTO.getTotalComDesconto());
    pagamento.setPedidoId(pagamentoDTO.getPedidoId());
    pagamento.setCupomId(pagamentoDTO.getCupomId());
    pagamento.setDesconto(pagamentoDTO.getDesconto());
    pagamento.setTransactionId(pagamentoDTO.getTransactionId());
    
    // Busca e armazena o nome do método de pagamento
    String nomeMetodoPagamento = metodoPagamentoService.getNomeMetodoPagamentoById(pagamentoDTO.getMetodoPagamentoId());
    
    // Verifica se o método é "Credito" ou "Debito"
    if ("Credito".equalsIgnoreCase(nomeMetodoPagamento) || "Debito".equalsIgnoreCase(nomeMetodoPagamento)) {
        PagCartaoModel pagCartao = new PagCartaoModel();
        pagCartao.setBandeiraCartao(pagamentoDTO.getBandeiraCartao());
        pagCartao.setNumero(pagamentoDTO.getNumero());
        pagCartao.setValidade(pagamentoDTO.getValidade());
        pagCartao.setNome(pagamentoDTO.getNome());
        pagCartao.setCpf(pagamentoDTO.getCpf());
        pagCartao.setAutorizacaoCod(pagamentoDTO.getAutorizacaoCod());
        pagCartao.setParcelas(pagamentoDTO.getParcelas());
        
        // Salva o pagamento por cartão, se necessário
        pagCartaoRepository.save(pagCartao);
    }
    
    return pagamentoRepository.save(pagamento);
}

}
