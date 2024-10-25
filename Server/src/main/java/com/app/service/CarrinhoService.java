package com.app.service;

import com.app.model.Carrinho;
import com.app.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoService {


    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public Carrinho saveCartItem(Carrinho carrinho){
        return carrinhoRepository.save(carrinho);
    }

    public List<Carrinho> getAllCartItems() {
        return carrinhoRepository.findAll();
    }
}
