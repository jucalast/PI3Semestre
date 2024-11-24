//package com.app.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.app.model.CupomModel;
//import com.app.model.PagamentoModel;
//import com.app.model.VendaModel;
//import com.app.service.PagamentoService;
//
///**
// * Classe de controle responsável por expor APIs relacionadas à entidade PagamentoModel.
// * Esta classe fornece endpoints para operações de CRUD e consultas de pagamentos.
// *
// * @author Kairo Chácara
// * @version 1.0
// * @since 2024-10-24
// *
// * @see com.app.service.PagamentoService
// */
//@RestController
//@RequestMapping("/api/pagamentos")
//public class PagamentoController {
//
//    @Autowired
//    private PagamentoService pagamentoService;
//
//    /**
//     * Endpoint para criar um pagamento completo.
//     * Este método recebe um objeto PagamentoCompletoComCartaoDTO que contém
//     * informações tanto do pagamento quanto do pagamento com cartão.
//     *
//     * @param pagamentoComCartaoDTO O objeto que representa o pagamento completo a ser criado.
//     * @return Resposta com o pagamento criado e status 201 Created.
//     */
//    @PostMapping("/completo")
//    public ResponseEntity<PagamentoModel> criarPagamentoCompleto(
//            @RequestBody PagamentoCompletoComCartaoDTO pagamentoComCartaoDTO) {
//
//        // Chama o serviço para criar o pagamento completo
//        PagamentoModel novoPagamento = pagamentoService.criarPagamentoCompleto(
//                pagamentoComCartaoDTO.getPagamentoDTO(),
//                pagamentoComCartaoDTO.getPagCartaoDTO());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(novoPagamento);
//    }
//
//    /**
//     * Endpoint para salvar ou atualizar um pagamento.
//     *
//     * @param pagamento O objeto que representa o pagamento a ser salvo ou atualizado.
//     * @return Resposta com o pagamento salvo e status 201 Created.
//     */
//    @PostMapping
//    public ResponseEntity<PagamentoModel> salvarPagamento(@RequestBody PagamentoModel pagamento) {
//        PagamentoModel pagamentoSalvo = pagamentoService.salvarPagamento(pagamento);
//        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoSalvo);
//    }
//
//    /**
//     * Endpoint para buscar um pagamento pelo ID.
//     *
//     * @param id O ID do pagamento a ser buscado.
//     * @return Resposta com o pagamento encontrado e status 200 OK, ou 404 Not Found se não encontrado.
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<PagamentoModel> buscarPagamentoPorId(@PathVariable Integer id) {
//        Optional<PagamentoModel> pagamento = pagamentoService.buscarPagamentoPorId(id);
//        return pagamento.map(ResponseEntity::ok)
//                        .orElse(ResponseEntity.notFound().build());
//    }
//
//    /**
//     * Endpoint para listar todos os pagamentos.
//     *
//     * @return Lista de todos os pagamentos com status 200 OK.
//     */
//    @GetMapping
//    public ResponseEntity<List<PagamentoModel>> listarTodosPagamentos() {
//        List<PagamentoModel> pagamentos = pagamentoService.listarTodosPagamentos();
//        return ResponseEntity.ok(pagamentos);
//    }
//
//    /**
//     * Endpoint para listar pagamentos por status.
//     *
//     * @param status O status do pagamento que deve ser buscado.
//     * @return Lista de pagamentos com o status especificado e status 200 OK.
//     */
//    @GetMapping("/status/{status}")
//    public ResponseEntity<List<PagamentoModel>> listarPagamentosPorStatus(@PathVariable PagamentoModel.StatusPagamento status) {
//        List<PagamentoModel> pagamentos = pagamentoService.listarPagamentosPorStatus(status);
//        return ResponseEntity.ok(pagamentos);
//    }
//
//    /**
//     * Endpoint para listar pagamentos por ID do pedido.
//     *
//     * @param pedidoId O ID do pedido associado aos pagamentos.
//     * @return Lista de pagamentos associados ao pedido e status 200 OK.
//     */
//    @GetMapping("/pedido/{pedidoId}")
//    public ResponseEntity<List<PagamentoModel>> listarPagamentosPorPedidoId(@PathVariable VendaModel pedidoId) {
//        List<PagamentoModel> pagamentos = pagamentoService.listarPagamentosPorPedidoId(pedidoId);
//        return ResponseEntity.ok(pagamentos);
//    }
//
//    /**
//     * Endpoint para listar pagamentos por ID do cupom.
//     *
//     * @param cupomId O ID do cupom associado aos pagamentos.
//     * @return Lista de pagamentos associados ao cupom e status 200 OK.
//     */
//    @GetMapping("/cupom/{cupomId}")
//    public ResponseEntity<List<PagamentoModel>> listarPagamentosPorCupomId(@PathVariable CupomModel cupomId) {
//        List<PagamentoModel> pagamentos = pagamentoService.listarPagamentosPorCupomId(cupomId);
//        return ResponseEntity.ok(pagamentos);
//    }
//
//    /**
//     * Endpoint para excluir um pagamento pelo ID.
//     *
//     * @param id O ID do pagamento a ser excluído.
//     * @return Resposta com status 204 No Content se excluído com sucesso.
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> excluirPagamento(@PathVariable Integer id) {
//        pagamentoService.excluirPagamento(id);
//        return ResponseEntity.noContent().build();
//    }
//}
