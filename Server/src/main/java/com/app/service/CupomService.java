//package com.app.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.app.model.CupomModel;
//import com.app.repository.CupomRepository;
//
///**
// * Serviço responsável por implementar a lógica de negócios relacionada a cupons.
// * Inclui operações de criação, leitura, atualização e remoção de cupons.
// *
// * @author Kairo Chácara
// * @version 1.0
// * @since 2024-10-15
// *
// * @see CupomRepository
// * @see CupomModel
// * @see CupomService
// * @see CupomController
// *
// */
//@Service
//public class CupomService {
//
//    private final CupomRepository cupomRepository;
//
//    @Autowired
//    public CupomService(CupomRepository cupomRepository) {
//        this.cupomRepository = cupomRepository;
//    }
//
//    /**
//     * Salva um novo cupom no banco de dados.
//     *
//     * @param cupom O cupom a ser salvo.
//     * @return O cupom salvo.
//     */
//    public CupomModel salvarCupom(CupomModel cupom) {
//        return cupomRepository.save(cupom);
//    }
//
//    /**
//     * Retorna uma lista de todos os cupons.
//     *
//     * @return Lista de cupons.
//     */
//    public List<CupomModel> listarTodosCupons() {
//        return cupomRepository.findAll();
//    }
//
//    /**
//     * Busca um cupom pelo ID.
//     *
//     * @param id O ID do cupom.
//     * @return O cupom correspondente ao ID, se encontrado.
//     */
//    public Optional<CupomModel> buscarCupomPorId(Integer id) {
//        return cupomRepository.findById(id);
//    }
//
//    /**
//     * Atualiza um cupom existente.
//     *
//     * @param id O ID do cupom a ser atualizado.
//     * @param novoCupom Os novos dados do cupom.
//     * @return O cupom atualizado.
//     */
//    public Optional<CupomModel> atualizarCupom(Integer id, CupomModel novoCupom) {
//        return cupomRepository.findById(id).map(cupomExistente -> {
//            cupomExistente.setNome(novoCupom.getNome());
//            cupomExistente.setDesconto(novoCupom.getDesconto());
//            cupomExistente.setDescricao(novoCupom.getDescricao());
//            cupomExistente.setDataValidade(novoCupom.getDataValidade());
//            return cupomRepository.save(cupomExistente);
//        });
//    }
//
//    /**
//     * Deleta um cupom pelo ID.
//     *
//     * @param id O ID do cupom a ser deletado.
//     */
//    public void deletarCupom(Integer id) {
//        cupomRepository.deleteById(id);
//    }
//
//    /**
//     * Busca uma lista de cupons dentro de um intervalo de datas.
//     *
//     * @param dataInicio A data de início do intervalo.
//     * @param dataFim A data de fim do intervalo.
//     * @return Lista de cupons dentro do intervalo de datas.
//     */
//    public List<CupomModel> buscarCuponsPorData(String dataInicio, String dataFim) {
//        return cupomRepository.findByDataValidadeBetween(dataInicio, dataFim);
//    }
//}
