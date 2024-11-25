package com.app.service;

import com.app.exceptions.ProdutoNotFoundException;
import com.app.model.*;
import com.app.model.pagamento.*;
import com.app.repository.*;
import com.app.repository.pagamento.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Serviço que fornece operações de negócio para o gerenciamento de pedidos.
 * Este serviço encapsula toda a lógica necessária para a criação e manutenção de pedidos,
 * incluindo interações com o banco de dados através de repositórios.
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PagamentoCreditoRepository pagamentoCreditoRepository;

    @Autowired
    private PagamentoDebitoRepository pagamentoDebitoRepository;

    @Autowired
    private PagamentoPixRepository pagamentoPixRepository;

    @Autowired
    private PagamentoBoletoRepository pagamentoBoletoRepository;

    public PedidoModel criarPedido(PedidoModel pedido, List<ProdutoPedidoModel> produtosPedido, PagamentoModel pagamento, Long userId) {
        UserModel usuario = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));
        pedido.setUsuario(usuario);

        BigDecimal total = BigDecimal.ZERO;
        for (ProdutoPedidoModel produtoPedido : produtosPedido) {
            Long produtoId = produtoPedido.getProduto().getId();
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ProdutoNotFoundException("Produto com ID " + produtoId + " não encontrado."));
            produtoPedido.setProduto(produto);
            total = total.add(produtoPedido.getSubtotal());
        }
        pedido.setTotal(total);

        PedidoModel pedidoSalvo = pedidoRepository.save(pedido);

        for (ProdutoPedidoModel produtoPedido : produtosPedido) {
            produtoPedido.setPedido(pedidoSalvo);
            produtoPedidoRepository.save(produtoPedido);
        }

        pagamento.setPedido(pedidoSalvo);
        pagamentoRepository.save(pagamento);

        return pedidoSalvo;
    }


    public MetodoPagamentoModel processarPagamento(Map<String, Object> pagamentoData, BigDecimal totalPedido, UserModel usuario) {
        String tipoPagamento = (String) pagamentoData.get("tipoPagamento");

        switch (TipoPagamento.valueOf(tipoPagamento.toUpperCase())) {
            case CREDITO:
                PagamentoCreditoModel pagamentoCredito = new PagamentoCreditoModel();
                pagamentoCredito.setCartao(buscarOuCriarCartao((Map<String, Object>) pagamentoData.get("cartao"), usuario));
                pagamentoCredito.setNumeroParcelas((Integer) pagamentoData.get("numeroParcelas"));
                pagamentoCredito.setTaxaJuros(TaxaPagamento.CREDITO.getTaxa());
                return pagamentoCredito;

            case DEBITO:
                PagamentoDebitoModel pagamentoDebito = new PagamentoDebitoModel();
                pagamentoDebito.setCartao(buscarOuCriarCartao((Map<String, Object>) pagamentoData.get("cartao"), usuario));
                pagamentoDebito.setTaxaTransacao(TaxaPagamento.DEBITO.getTaxa());
                return pagamentoDebito;

            case PIX:
                PagamentoPixModel pagamentoPix = new PagamentoPixModel();
                pagamentoPix.setChavePix((String) pagamentoData.get("chavePix"));
                pagamentoPix.setTaxaPix(TaxaPagamento.PIX.getTaxa());
                return pagamentoPix;

            case BOLETO:
                PagamentoBoletoModel pagamentoBoleto = new PagamentoBoletoModel();
                pagamentoBoleto.setCodigoBoleto((String) pagamentoData.get("codigoBoleto"));
                pagamentoBoleto.setDataVencimento(LocalDate.parse((String) pagamentoData.get("dataVencimento")));
                pagamentoBoleto.setTaxaBoleto(TaxaPagamento.BOLETO.getTaxa());
                return pagamentoBoleto;

            default:
                throw new IllegalArgumentException("Tipo de pagamento inválido: " + tipoPagamento);
        }
    }


    private CartaoModel buscarOuCriarCartao(Map<String, Object> cartaoData, UserModel usuario) {
        String numeroCartao = (String) cartaoData.get("numeroCartao");
        return cartaoRepository.findById(numeroCartao).orElseGet(() -> {
            CartaoModel novoCartao = new CartaoModel();
            novoCartao.setNumeroCartao(numeroCartao);
            novoCartao.setNomeTitular((String) cartaoData.get("nomeTitular"));
            novoCartao.setValidade((String) cartaoData.get("validade"));
            novoCartao.setBandeira((String) cartaoData.get("bandeira"));
            novoCartao.setCpfTitular((String) cartaoData.get("cpfTitular"));
            novoCartao.setUser(usuario);
            return cartaoRepository.save(novoCartao);
        });
    }


}