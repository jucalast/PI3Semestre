package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.PagCartaoModel;
import com.app.repository.PagCartaoRepository;

/**
 * Classe de serviço responsável por realizar operações relacionadas à entidade PagCartaoModel.
 * Esta classe contém a lógica de negócios para pagamentos com cartão, incluindo criação, consulta, atualização e remoção.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 * 
 * @see com.app.repository.PagCartaoRepository
 */
@Service
public class PagCartaoService {

    private final PagCartaoRepository pagCartaoRepository;

    /**
     * Construtor da classe PagCartaoService que injeta o repositório de pagamentos com cartão.
     * 
     * @param pagCartaoRepository O repositório para acessar os dados de pagamentos com cartão no banco de dados.
     */
    @Autowired
    public PagCartaoService(PagCartaoRepository pagCartaoRepository) {
        this.pagCartaoRepository = pagCartaoRepository;
    }

    /**
     * Salva um novo pagamento com cartão no banco de dados.
     *
     * @param pagCartao Objeto que representa o pagamento com cartão a ser salvo.
     * @return O pagamento com cartão salvo.
     */
    public PagCartaoModel salvarPagCartao(PagCartaoModel pagCartao) {
        return pagCartaoRepository.save(pagCartao);
    }

    /**
     * Lista todos os pagamentos com cartão cadastrados no banco de dados.
     *
     * @return Uma lista com todos os pagamentos com cartão disponíveis.
     */
    public List<PagCartaoModel> listarPagCartao() {
        return pagCartaoRepository.findAll();
    }

    /**
     * Obtém um pagamento com cartão pelo ID.
     *
     * @param id O ID do pagamento com cartão a ser buscado.
     * @return Um objeto Optional que contém o pagamento com cartão, se encontrado.
     */
    public Optional<PagCartaoModel> obterPagCartaoPorId(Long id) {
        return pagCartaoRepository.findById(id);
    }

    /**
     * Atualiza um pagamento com cartão existente.
     *
     * @param id O ID do pagamento com cartão a ser atualizado.
     * @param pagCartao Os novos dados do pagamento com cartão.
     * @return Um Optional com o pagamento com cartão atualizado, ou vazio se o ID não for encontrado.
     */
    public Optional<PagCartaoModel> atualizarPagCartao(Long id, PagCartaoModel pagCartao) {
        return pagCartaoRepository.findById(id).map(existingPagCartao -> {
            existingPagCartao.setBandeiraCartao(pagCartao.getBandeiraCartao());
            existingPagCartao.setNumero(pagCartao.getNumero());
            existingPagCartao.setValidade(pagCartao.getValidade());
            existingPagCartao.setNome(pagCartao.getNome());
            existingPagCartao.setCpf(pagCartao.getCpf());
            existingPagCartao.setAutorizacaoCod(pagCartao.getAutorizacaoCod());
            existingPagCartao.setParcelas(pagCartao.getParcelas());
            return pagCartaoRepository.save(existingPagCartao);
        });
    }

    /**
     * Exclui um pagamento com cartão pelo ID.
     *
     * @param id O ID do pagamento com cartão a ser excluído.
     */
    public void deletarPagCartao(Long id) {
        pagCartaoRepository.deleteById(id);
    }

    /**
     * Encontra um pagamento com cartão pelo ID do pagamento associado.
     *
     * @param pagamentoId ID do pagamento associado
     * @return Optional de PagCartaoModel
     */
    public Optional<PagCartaoModel> obterPagCartaoPorPagamentoId(Long pagamentoId) {
        return pagCartaoRepository.findByPagamentoId(pagamentoId);
    }

    /**
     * Conta quantos pagamentos com cartão existem para uma determinada bandeira.
     *
     * @param bandeiraCartao A bandeira do cartão
     * @return Número de registros encontrados
     */
    public long contarPorBandeiraCartao(String bandeiraCartao) {
        return pagCartaoRepository.countByBandeiraCartao(bandeiraCartao);
    }

    /**
     * Busca todos os pagamentos com cartão com base no número de parcelas.
     *
     * @param parcelas Número de parcelas
     * @return Lista de PagCartaoModel
     */
    public List<PagCartaoModel> listarPorParcelas(Integer parcelas) {
        return pagCartaoRepository.findByParcelas(parcelas);
    }

    /**
     * Busca um pagamento com cartão pelo CPF do titular.
     *
     * @param cpf CPF do titular
     * @return Optional de PagCartaoModel
     */
    public Optional<PagCartaoModel> obterPagCartaoPorCpf(String cpf) {
        return pagCartaoRepository.findByCpf(cpf);
    }
}
