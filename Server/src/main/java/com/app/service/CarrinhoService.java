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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public boolean insertProductOnUserCart(Long userId, Long productId) {
        try {
            if (userRepository.existsById((long) 1)) {
                System.out.println("Usuario existe" + userRepository.findById((long) 1));
            } else {
                System.out.println("Usuario não existe");
            }
            if (produtoRepository.existsById(productId)) {
                System.out.println("Produto existe" + produtoRepository.findById(productId));
            } else {
                System.out.println("Produto não existe");
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    public List<Map<String, Object>> getProductsOnUserCart(Long id_user) {
        List<Carrinho> carrinhos = carrinhoRepository.findByUserModelId(id_user);
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
}

