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
//import com.app.model.ProdutoPedidoModel;
//import com.app.service.ProdutoPedidoService;
//
///**
// * Controlador que expõe as APIs relacionadas à entidade ProdutoPedidoModel.
// * Permite operações de CRUD e consultas específicas sobre produtos de pedidos.
// *
// * Bibliotecas utilizadas:
// *
// * - com.app.model.ProdutoPedidoModel: Classe que representa o modelo de dados de um produto em um pedido.
// * - com.app.service.ProdutoPedidoService: Classe de serviço que contém a lógica de negócios relacionada aos produtos de pedidos.
// * - org.springframework.beans.factory.annotation.Autowired: Permite a injeção de dependência em classes Spring.
// * - org.springframework.http.HttpStatus: Representa os códigos de status HTTP, utilizados para construir respostas.
// * - org.springframework.http.ResponseEntity: Classe que encapsula a resposta HTTP, incluindo o corpo e os cabeçalhos.
// * - org.springframework.web.bind.annotation.*: Contém anotações para definir controladores e mapear requisições HTTP para métodos (como @RestController, @RequestMapping, @GetMapping, @PostMapping, etc.).
// *
// * @author Kairo
// * @version 1.0
// * @since 2024-10-19
// */
//@RestController
//@RequestMapping("/api/produtoPedido")
//public class ProdutoPedidoController {
//
//    @Autowired
//    private ProdutoPedidoService produtoPedidoService;
//
//    /**
//     * Retorna a lista de todos os produtos de pedidos cadastrados.
//     *
//     * @return ResponseEntity com a lista de ProdutoPedidoModel e status HTTP OK.
//     */
//    @GetMapping
//    public ResponseEntity<List<ProdutoPedidoModel>> listarTodos() {
//        List<ProdutoPedidoModel> produtoPedidos = produtoPedidoService.listarTodos();
//        return new ResponseEntity<>(produtoPedidos, HttpStatus.OK);
//    }
//
//    /**
//     * Retorna um produto de pedido específico pelo ID.
//     *
//     * @param id ID do produto no pedido.
//     * @return ResponseEntity com ProdutoPedidoModel e status HTTP OK ou NOT FOUND.
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<ProdutoPedidoModel> buscarPorId(@PathVariable Integer id) {
//        Optional<ProdutoPedidoModel> produtoPedido = produtoPedidoService.buscarPorId(id);
//        return produtoPedido.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    /**
//     * Cadastra ou atualiza um produto em um pedido.
//     *
//     * @param produtoPedido O ProdutoPedidoModel a ser salvo ou atualizado.
//     * @return ResponseEntity com ProdutoPedidoModel e status HTTP CREATED.
//     */
//    @PostMapping
//    public ResponseEntity<ProdutoPedidoModel> salvar(@RequestBody ProdutoPedidoModel produtoPedido) {
//        ProdutoPedidoModel novoProdutoPedido = produtoPedidoService.salvarOuAtualizar(produtoPedido);
//        return new ResponseEntity<>(novoProdutoPedido, HttpStatus.CREATED);
//    }
//
//
//
//    /**
//     * Retorna a lista de produtos relacionados a um pedido específico.
//     *
//     * @param pedidoId ID do pedido.
//     * @return ResponseEntity com a lista de ProdutoPedidoModel e status HTTP OK.
//     */
//    @GetMapping("/pedido/{pedidoId}")
//    public ResponseEntity<List<ProdutoPedidoModel>> buscarPorPedidoId(@PathVariable Integer pedidoId) {
//        List<ProdutoPedidoModel> produtosDoPedido = produtoPedidoService.buscarPorPedidoId(pedidoId);
//        return new ResponseEntity<>(produtosDoPedido, HttpStatus.OK);
//    }
//
//
//}
