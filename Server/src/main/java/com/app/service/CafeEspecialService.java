package com.app.service;

import com.app.model.CafeEspecial;
import com.app.repository.CafeEspecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CafeEspecialService {

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

    public CafeEspecial saveCafeEspecial(CafeEspecial cafeEspecial) {
        return cafeEspecialRepository.save(cafeEspecial);
    }

    public Optional<CafeEspecial> getCafeEspecialById(Long id) {
        return cafeEspecialRepository.findById(id);
    }

    public void deleteCafeEspecial(Long id) {
        cafeEspecialRepository.deleteById(id);
    }

    // Outros métodos de serviço podem ser adicionados aqui
}
