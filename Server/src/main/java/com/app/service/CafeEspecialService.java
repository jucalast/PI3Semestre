package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.app.model.CafeEspecial;
import com.app.repository.CafeEspecialRepository;



@Service
public class CafeEspecialService {

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;
    
    @Transactional
    public CafeEspecial createCafeEspecial(CafeEspecial cafeEspecial) {
        System.out.println("Criando Café Especial: " + cafeEspecial);
        CafeEspecial createdCafeEspecial = cafeEspecialRepository.save(cafeEspecial);
        System.out.println("Café Especial Criado: " + createdCafeEspecial);
        return createdCafeEspecial;
    }
    

    public Optional<CafeEspecial> getCafeEspecialById(Long id) {
        return cafeEspecialRepository.findById(id);
    }
}
