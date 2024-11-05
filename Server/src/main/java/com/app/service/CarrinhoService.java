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

@Service
public class CarrinhoService {


    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    //Verificar com o novo código do produto
    public boolean insertProductOnUserCart(Long idUser, Long productId) {
        Optional<Carrinho> carrinhoExistente = carrinhoRepository.findByUserModelIdAndProdutoId(idUser, productId);
        if(carrinhoExistente.isPresent()){
            deleteProductOnUserCart(idUser, productId);
            return true;
        }
        try{
            Optional<UserModel> userModel = userRepository.findById(idUser);
            Optional<Produto> produto = produtoRepository.findById(productId);

            if(userModel.isEmpty()){
                throw new NoSuchElementException("Erro: Usuário não encontrado");
            }
            if(produto.isEmpty()){
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
        } catch (Exception e){
            System.out.print(e);
            return false;
        }
    }


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

    @Transactional
    public boolean deleteProductOnUserCart(Long idUser, Long productId) {
        try{
            if(carrinhoRepository.existsByUserModelIdAndProdutoId(idUser, productId)){
                carrinhoRepository.deleteByUserModelIdAndProdutoId(idUser, productId);
                return true;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateQuantityOnUserCart(Long idUser, Long productId, int quantity) {
        Optional<Carrinho> carrinhoExistente = carrinhoRepository.findByUserModelIdAndProdutoId(idUser,productId);
            if (carrinhoExistente.isPresent()) {
                try {
                    Carrinho carrinho = carrinhoExistente.get();
                    carrinho.setQuantidade(quantity);
                    carrinhoRepository.save(carrinho);
                    return true;
                } catch (Exception e) {
                    System.out.println("Erro: Não foi possível alterar a quantidade" + e.getMessage());
                    return false;
                }
            } else {
                System.out.println("Erro: Dados não encontrados");
                return false;
            }
        }
    }

