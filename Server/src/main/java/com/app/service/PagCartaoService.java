package com.app.service;

/* 
* Importa a classe PagCartaoModel para interagir com a entidade
* import com.app.model.PagCartaoModel;
* 
* Importa a interface PagCartaoRepository para operações de acesso ao banco de dados
* import com.app.repository.PagCartaoRepository;
* 
* Importa a anotação @Service para indicar que a classe é um serviço
* import org.springframework.stereotype.Service;
* 
* Importa a classe ResponseStatusException para tratamento de exceções
* import org.springframework.web.server.ResponseStatusException;
* 
* Importa a classe Optional para manipulação de objetos que podem estar ausentes
* import java.util.Optional;
* 
* Importa a lista para manipulação de coleções
* import java.util.List;
*/
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.model.PagCartaoModel;
import com.app.repository.PagCartaoRepository;

/**
 * Serviço para a entidade 'PagCartaoModel'.
 * Esta classe contém a lógica de negócios relacionada a pagamentos realizados
 * com cartão de crédito, incluindo operações de criação, leitura, atualização e
 * exclusão de registros.
 * 
 * A anotação @Service indica que esta classe é um serviço Spring,
 * permitindo a injeção de dependência e a implementação de lógica de negócios.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-20
 */
@Service
public class PagCartaoService {

    private final PagCartaoRepository pagCartaoRepository;

    /**
     * Construtor que injeta o repositório de pagamentos com cartão.
     * 
     * @param pagCartaoRepository O repositório a ser injetado.
     */
    public PagCartaoService(PagCartaoRepository pagCartaoRepository) {
        this.pagCartaoRepository = pagCartaoRepository;
    }

    /**
     * Salva um novo pagamento com cartão no banco de dados.
     * 
     * @param pagCartaoModel O modelo do pagamento a ser salvo.
     * @return O pagamento salvo.
     */
    public PagCartaoModel salvar(PagCartaoModel pagCartaoModel) {
        return pagCartaoRepository.save(pagCartaoModel);
    }

    /**
     * Busca um pagamento com cartão pelo ID.
     * 
     * @param id O ID do pagamento a ser buscado.
     * @return O pagamento encontrado.
     * @throws ResponseStatusException Se o pagamento não for encontrado.
     */
    public PagCartaoModel buscarPorId(Long id) {
        return pagCartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Pagamento com cartão não encontrado com o ID: " + id));
    }

    /**
     * Lista todos os pagamentos com cartão registrados no banco de dados.
     * 
     * @return Uma lista de pagamentos com cartão.
     */
    public List<PagCartaoModel> listarTodos() {
        return pagCartaoRepository.findAll();
    }

    /**
     * Atualiza um pagamento com cartão existente.
     * 
     * @param id O ID do pagamento a ser atualizado.
     * @param pagCartaoModel O modelo atualizado do pagamento.
     * @return O pagamento atualizado.
     * @throws ResponseStatusException Se o pagamento não for encontrado.
     */
    public PagCartaoModel atualizar(Long id, PagCartaoModel pagCartaoModel) {
        if (!pagCartaoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Pagamento com cartão não encontrado com o ID: " + id);
        }
        pagCartaoModel.setId(id);
        return pagCartaoRepository.save(pagCartaoModel);
    }

    /**
     * Remove um pagamento com cartão pelo ID.
     * 
     * @param id O ID do pagamento a ser removido.
     * @throws ResponseStatusException Se o pagamento não for encontrado.
     */
    public void remover(Long id) {
        if (!pagCartaoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Pagamento com cartão não encontrado com o ID: " + id);
        }
        pagCartaoRepository.deleteById(id);
    }
}
