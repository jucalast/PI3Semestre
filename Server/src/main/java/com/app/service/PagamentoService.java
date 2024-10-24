package com.app.service;

import com.app.model.PagamentoModel;
import com.app.model.PagCartaoModel;
import com.app.repository.PagamentoRepository;
import com.app.repository.PagCartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar as operações relacionadas à entidade 'PagamentoModel',
 * incluindo lógica de negócio e integração com o repositório de pagamentos.
 * Este serviço também cuida da inserção de registros tanto na tabela de pagamentos
 * quanto na tabela 'pagCartao', quando apropriado.
 * 
 * A anotação @Service indica que esta classe é um serviço Spring.
 * 
 * A anotação @Transactional garante que a operação seja atômica, ou seja,
 * se qualquer parte da transação falhar, nenhuma modificação será salva no banco de dados.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 */
@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PagCartaoRepository pagCartaoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, PagCartaoRepository pagCartaoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagCartaoRepository = pagCartaoRepository;
    }

    /**
     * Salva um novo pagamento e, se for um pagamento com cartão, também salva
     * os detalhes do cartão na tabela 'pagCartao'.
     * 
     * @param pagamentoModel O modelo do pagamento a ser salvo.
     * @param pagCartaoModel O modelo dos detalhes do pagamento com cartão (se aplicável).
     * @return O pagamento salvo.
     */
    @Transactional
    public PagamentoModel salvarPagamentoComCartao(PagamentoModel pagamentoModel, Optional<PagCartaoModel> pagCartaoModel) {
        // Salva o pagamento na tabela 'pagamentos'
        PagamentoModel pagamentoSalvo = pagamentoRepository.save(pagamentoModel);

        // Se for um pagamento com cartão (método de pagamento específico), salva na tabela 'pagCartao'
        if (pagCartaoModel.isPresent()) {
            PagCartaoModel cartao = pagCartaoModel.get();
            cartao.setPagamentoId(pagamentoSalvo.getId());  // Vincula o ID do pagamento
            pagCartaoRepository.save(cartao);  // Salva os detalhes do cartão
        }

        return pagamentoSalvo;
    }

    /**
     * Busca um pagamento pelo ID.
     * 
     * @param id O ID do pagamento a ser buscado.
     * @return O pagamento encontrado.
     * @throws ResponseStatusException Se o pagamento não for encontrado.
     */
    public PagamentoModel buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Pagamento não encontrado com o ID: " + id));
    }

    /**
     * Lista todos os pagamentos registrados no banco de dados.
     * 
     * @return Uma lista de pagamentos.
     */
    public List<PagamentoModel> listarTodos() {
        return pagamentoRepository.findAll();
    }

    /**
     * Atualiza um pagamento existente.
     * 
     * @param id O ID do pagamento a ser atualizado.
     * @param pagamentoModel O modelo atualizado do pagamento.
     * @return O pagamento atualizado.
     * @throws ResponseStatusException Se o pagamento não for encontrado.
     */
    public PagamentoModel atualizar(Long id, PagamentoModel pagamentoModel) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Pagamento não encontrado com o ID: " + id);
        }
        pagamentoModel.setId(id); // Definir o ID no modelo atualizado
        return pagamentoRepository.save(pagamentoModel); // Salvar o pagamento atualizado
    }

    /**
     * Remove um pagamento pelo ID.
     * 
     * @param id O ID do pagamento a ser removido.
     * @throws ResponseStatusException Se o pagamento não for encontrado.
     */
    public void remover(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Pagamento não encontrado com o ID: " + id);
        }
        pagamentoRepository.deleteById(id); // Remove o pagamento
    }
}
