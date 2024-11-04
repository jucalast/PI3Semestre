package com.app.service;

/**
 * Bibliotecas utilizadas:
 * 
 * - java.util.ArrayList: Classe que implementa uma lista dinâmica que pode ser redimensionada,
 *   permitindo a adição e remoção de elementos.
 * - java.util.List: Interface que representa uma coleção ordenada de elementos, permitindo
 *   operações como adição, remoção e acesso a elementos por índice.
 * - java.util.Optional: Classe que pode ou não conter um valor, usada para evitar o uso
 *   de valores nulos e melhorar a segurança no código.
 * - org.springframework.beans.factory.annotation.Autowired: Anotação utilizada para injeção
 *   de dependência em classes do Spring, facilitando a gestão de objetos e seus ciclos de vida.
 * - org.springframework.stereotype.Service: Anotação que marca a classe como um serviço,
 *   permitindo que o Spring a detecte e a registre como um bean.
 * - com.app.DTO.MetodoPagamentoNomeTaxaDTO: Classe DTO (Data Transfer Object) usada para transferir
 *   dados entre camadas, especificamente contendo informações sobre nome e taxa de métodos de pagamento.
 * - com.app.model.MetodoPagamentoModel: Classe que representa o modelo de dados para métodos
 *   de pagamento, contendo atributos e comportamentos relacionados a um método de pagamento específico.
 * - com.app.repository.MetodoPagamentoRepository: Interface que fornece métodos para interagir
 *   com o banco de dados relacionados à entidade MetodoPagamentoModel, estendendo JpaRepository.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.DTO.MetodoPagamentoNomeTaxaDTO;
import com.app.model.MetodoPagamentoModel;
import com.app.repository.MetodoPagamentoRepository;

/**
 * Classe de serviço responsável por realizar operações relacionadas à entidade MetodoPagamentoModel.
 * Esta classe contém a lógica de negócios para métodos de pagamento, incluindo criação, consulta, atualização e remoção.
 * 
 * @author Kairo Chácarra
 * @version 1.0
 * @since 2024-10-14
 * 
 * @see com.app.repository.MetodoPagamentoRepository
 * 
 */
@Service
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;

   /**
 * Construtor da classe MetodoPagamentoService que injeta o repositório de métodos de pagamento.
 * Este construtor é utilizado pelo Spring para criar uma instância do serviço e injetar
 * a dependência do repositório de métodos de pagamento.
 *
 * @param metodoPagamentoRepository O repositório que fornece operações CRUD para
 *                                   a entidade MetodoPagamentoModel.
 */
@Autowired
public MetodoPagamentoService(MetodoPagamentoRepository metodoPagamentoRepository) {
    this.metodoPagamentoRepository = metodoPagamentoRepository;
}

    /**
     * Salva um novo método de pagamento no banco de dados.
     *
     * @param metodoPagamento Objeto que representa o método de pagamento a ser salvo.
     * @return O método de pagamento salvo.
     */
    public MetodoPagamentoModel salvarMetodoPagamento(MetodoPagamentoModel metodoPagamento) {
        return metodoPagamentoRepository.save(metodoPagamento);
    }

    /**
     * Lista todos os métodos de pagamento cadastrados no banco de dados.
     *
     * @return Uma lista com todos os métodos de pagamento disponíveis.
     */
    public List<MetodoPagamentoModel> listarMetodosPagamento() {
        return metodoPagamentoRepository.findAll();
    }

    /**
     * Obtém um método de pagamento pelo ID.
     *
     * @param id O ID do método de pagamento a ser buscado.
     * @return Um objeto Optional que contém o método de pagamento, se encontrado.
     */
    public Optional<MetodoPagamentoModel> obterMetodoPagamentoPorId(Integer id) {
        return metodoPagamentoRepository.findById(id);
    }

    /**
     * Atualiza um método de pagamento existente.
     *
     * @param id O ID do método de pagamento a ser atualizado.
     * @param metodoPagamento Os novos dados do método de pagamento.
     * @return Um Optional com o método de pagamento atualizado, ou vazio se o ID não for encontrado.
     */
    public Optional<MetodoPagamentoModel> atualizarMetodoPagamento(Integer id, MetodoPagamentoModel metodoPagamento) {
        return metodoPagamentoRepository.findById(id).map(existingMetodo -> {
            existingMetodo.setNome(metodoPagamento.getNome());
            existingMetodo.setTaxa(metodoPagamento.getTaxa());
            existingMetodo.setEmpresa(metodoPagamento.getEmpresa());
            return metodoPagamentoRepository.save(existingMetodo);
        });
    }

    /**
     * Exclui um método de pagamento pelo ID.
     *
     * @param id O ID do método de pagamento a ser excluído.
     */
    public void deletarMetodoPagamento(Integer id) {
        metodoPagamentoRepository.deleteById(id);
    }

    /**
     * Busca métodos de pagamento pelo nome.
     *
     * @param nome O nome do método de pagamento (ex: Boleto, Cartão de Crédito).
     * @return Uma lista de métodos de pagamento que correspondem ao nome fornecido.
     */
    public List<MetodoPagamentoModel> buscarPorNome(String nome) {
        return metodoPagamentoRepository.findByNome(nome);
    }

    /**
     * Lista métodos de pagamento com os campos 'nome' e 'taxa', ordenados em ordem crescente.
     *
     * @return Uma lista de objetos DTO que contêm o nome e a taxa dos métodos de pagamento.
     */
    public List<MetodoPagamentoNomeTaxaDTO> listarNomeETaxaOrdenado() {
        List<Object[]> resultados = metodoPagamentoRepository.listPorNomeTaxa();
        List<MetodoPagamentoNomeTaxaDTO> listaDTO = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String nome = (String) resultado[0];
            Float taxa = ((Number) resultado[1]).floatValue();
            MetodoPagamentoNomeTaxaDTO dto = new MetodoPagamentoNomeTaxaDTO(nome, taxa);
            listaDTO.add(dto);
        }

        return listaDTO;
    }
    public String getNomeMetodoPagamentoById(Integer id) {
        return metodoPagamentoRepository.findNomeById(id).orElse("Método de pagamento não encontrado");
    }
}
