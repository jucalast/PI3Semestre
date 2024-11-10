package com.app.service;

import com.app.model.Carrinho;
import com.app.model.Produto;
import com.app.model.UserModel;
import com.app.repository.CarrinhoRepository;
import com.app.repository.ProdutoRepository;
import com.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe de serviço responsável pela lógica de negocios relacionada a entidade
 * Carrinho.
 *
 * @author Ricardo L. Ferreira
 */
@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Create do carrinho - Método responsável pela inserção de um novo registro
     * na tabela carrinho. Caso o item já esteja no carrinho, então é feita a
     * remoção do mesmo é feita ao acionar o método deleteProductOnUserCart()
     *
     * @param idUser O id do usuário.
     * @param productId O id do produto.
     * @return Retorna true em caso de sucesso e false em caso de falha.
     */
    @Transactional
    public boolean insertProductOnUserCart(Long idUser, Long productId) {
        Optional<Carrinho> carrinhoExistente = carrinhoRepository.findByUserModelIdAndProdutoId(idUser, productId);
        if (carrinhoExistente.isPresent()) {
            deleteProductOnUserCart(idUser, productId);
            return true;
        }
        try {
            Optional<UserModel> userModel = userRepository.findById(idUser);
            Optional<Produto> produto = produtoRepository.findById(productId);

            if (userModel.isEmpty()) {
                throw new NoSuchElementException("Erro: Usuário não encontrado");
            }
            if (produto.isEmpty()) {
                throw new NoSuchElementException("Erro: Produto não encontrado");
            }

            UserModel getUsetModel = userModel.get();
            Produto getProduto = produto.get();
            Carrinho carrinho = new Carrinho();
            carrinho.setUserModel(getUsetModel);
            carrinho.setProduto(getProduto);
            carrinho.setQuantidade(1);
            Carrinho savedCart = carrinhoRepository.save(carrinho);
            return true;
        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }

    /**
     * Read do carrinho - Método responsável por fazer a leitura/trazer as
     * registros existentes no banco de dados relacionados ao carrinho.
     *
     * @param idUser O id do usuário.
     * @return Retorna um objeto do tipo Map, que guarda a chave e valor de cada
     * atributo presente no carrinho.
     */
    public List<Map<String, Object>> getProductsOnUserCart(Long idUser) {
        List<Carrinho> carrinhos = carrinhoRepository.findByUserModelId(idUser);

        return carrinhos.stream()
                .map(carrinho -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("carrinhoId", carrinho.getId());
                    map.put("userId", carrinho.getUserModel().getId());
                    map.put("produtoId", carrinho.getProduto().getId());
                    map.put("imagem_produto", carrinho.getProduto().getImagem());
                    map.put("nome_produto", carrinho.getProduto().getNome());
                    map.put("preco_produto", carrinho.getProduto().getPreco());
                    map.put("quantidade", carrinho.getQuantidade());
                    return map;
                })
                .collect(Collectors.toList());
    }

    /**
     * Update do carrinho - Método responsável por fazer a atualização da
     * quantia unitária de cada produto presente no carrinho.
     *
     * @param idUser O id do usuário.
     * @param productId O id do produto.
     * @param quantity A nova quantidade.
     * @return Retorna true em caso de sucesso e false em caso de falha.
     */
    public boolean updateQuantityOnUserCart(Long idUser, Long productId, int quantity) {
        Optional<Carrinho> carrinhoExistente = carrinhoRepository.findByUserModelIdAndProdutoId(idUser, productId);

        if (carrinhoExistente.isPresent()) {
            try {
                Carrinho carrinho = carrinhoExistente.get();
                // Verifique se a quantidade é válida (não negativa)
                if (quantity > 0) {
                    carrinho.setQuantidade(quantity);
                    carrinhoRepository.save(carrinho); // Salva a atualização
                    return true;
                } else {
                    System.out.println("Erro: Quantidade inválida");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Erro ao atualizar quantidade no carrinho: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Erro: Produto não encontrado no carrinho do usuário");
            return false;
        }
    }

    /**
     * Delete do carrinho - Método responsável por remover um item do carrinho.
     *
     * @param idUser O id do usuário.
     * @param productId o id do produto.
     * @return Retorna true em caso de sucesso e false em caso de falha.
     */
    @Transactional
    public boolean deleteProductOnUserCart(Long idUser, Long productId) {
        try {
            if (carrinhoRepository.existsByUserModelIdAndProdutoId(idUser, productId)) {
                carrinhoRepository.deleteByUserModelIdAndProdutoId(idUser, productId);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
