package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.DTO.PagCartaoDTO;
import com.app.DTO.PagamentoCompletoDTO;
import com.app.model.CupomModel;
import com.app.model.PagCartaoModel;
import com.app.model.PagamentoModel;
import com.app.model.PedidoModel;
import com.app.repository.PagCartaoRepository;
import com.app.repository.PagamentoRepository;

/**
 * Classe de serviço responsável por realizar operações relacionadas à entidade PagamentoModel.
 * Esta classe contém a lógica de negócios para pagamentos, incluindo criação, consulta, atualização e remoção.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 * 
 * @see com.app.repository.PagamentoRepository
 */
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    
    @Autowired
    private MetodoPagamentoService metodoPagamentoService;

    private final PagCartaoRepository pagCartaoRepository;

    /**
     * Construtor da classe PagamentoService que injeta o repositório de pagamentos com cartão.
     * 
     * @param pagCartaoRepository O repositório para acessar os dados de pagamentos com cartão no banco de dados.
     */
    @Autowired
    public PagamentoService(PagCartaoRepository pagCartaoRepository) {
        this.pagCartaoRepository = pagCartaoRepository;
    }

    /**
     * Salva ou atualiza um pagamento.
     *
     * @param pagamento Objeto que representa o pagamento a ser salvo.
     * @return O pagamento salvo.
     */
    public PagamentoModel salvarPagamento(PagamentoModel pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    /**
     * Busca um pagamento pelo ID.
     *
     * @param id ID do pagamento a ser buscado.
     * @return Um Optional que contém o pagamento, se encontrado.
     */
    public Optional<PagamentoModel> buscarPagamentoPorId(Integer id) {
        return pagamentoRepository.findById(id);
    }

    /**
     * Lista todos os pagamentos cadastrados no banco de dados.
     *
     * @return Uma lista com todos os pagamentos disponíveis.
     */
    public List<PagamentoModel> listarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    /**
     * Lista pagamentos por status.
     *
     * @param statusPagamento Status do pagamento.
     * @return Lista de pagamentos que correspondem ao status.
     */
    public List<PagamentoModel> listarPagamentosPorStatus(PagamentoModel.StatusPagamento statusPagamento) {
        return pagamentoRepository.findByStatusPagamento(statusPagamento);
    }

    /**
     * Lista pagamentos por ID do pedido.
     *
     * @param pedidoId ID do pedido.
     * @return Lista de pagamentos associados ao ID do pedido.
     */
    public List<PagamentoModel> listarPagamentosPorPedidoId(PedidoModel pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId);
    }

    /**
     * Lista pagamentos por ID do cupom.
     *
     * @param cupomId ID do cupom.
     * @return Lista de pagamentos associados ao ID do cupom.
     */
    public List<PagamentoModel> listarPagamentosPorCupomId(CupomModel cupomId) {
        return pagamentoRepository.findByCupomId(cupomId);
    }

    /**
     * Exclui um pagamento pelo ID.
     *
     * @param id ID do pagamento a ser excluído.
     */
    public void excluirPagamento(Integer id) {
        pagamentoRepository.deleteById(id);
    }

    /**
     * Cria um pagamento completo, incluindo detalhes do pagamento e do cartão, se necessário.
     *
     * @param pagamentoDTO Dados do pagamento a ser criado.
     * @param pagCartaoDTO Dados do pagamento com cartão.
     * @return O pagamento completo salvo.
     */
    @Transactional
    public PagamentoModel criarPagamentoCompleto(PagamentoCompletoDTO pagamentoDTO, PagCartaoDTO pagCartaoDTO) {
        PagamentoModel pagamento = new PagamentoModel();
        pagamento.setMetodoPagamentoId(pagamentoDTO.getMetodoPagamentoId());
        pagamento.setDataHora(pagamentoDTO.getDataHora());
        pagamento.setTotal(pagamentoDTO.getTotal());
        pagamento.setTotalComDesconto(pagamentoDTO.getTotalComDesconto());
        pagamento.setPedidoId(pagamentoDTO.getPedidoId());
        pagamento.setCupomId(pagamentoDTO.getCupomId());
        pagamento.setDesconto(pagamentoDTO.getDesconto());
        pagamento.setTransactionId(pagamentoDTO.getTransactionId());
        
        String nomeMetodoPagamento = metodoPagamentoService.getNomeMetodoPagamentoById(pagamentoDTO.getMetodoPagamentoId().getId());
        
        if (nomeMetodoPagamento != null && 
            ("Credito".equalsIgnoreCase(nomeMetodoPagamento) || "Debito".equalsIgnoreCase(nomeMetodoPagamento))) {
            PagamentoModel id = pagamentoRepository.findTopByOrderByIdDesc();
        
            if (pagCartaoDTO != null) {
                PagCartaoModel pagCartao = new PagCartaoModel();
                pagCartao.setPagamentoId(id);
                pagCartao.setBandeiraCartao(pagCartaoDTO.getBandeiraCartao());
                pagCartao.setNumero(pagCartaoDTO.getNumero());
                pagCartao.setValidade(pagCartaoDTO.getValidade());
                pagCartao.setNome(pagCartaoDTO.getNome());
                pagCartao.setCpf(pagCartaoDTO.getCpf());
                pagCartao.setAutorizacaoCod(pagCartaoDTO.getAutorizacaoCod());
                pagCartao.setParcelas(pagCartaoDTO.getParcelas());
                
                pagCartaoRepository.save(pagCartao);
            } 
        } 
        return pagamentoRepository.save(pagamento);
    }
}
