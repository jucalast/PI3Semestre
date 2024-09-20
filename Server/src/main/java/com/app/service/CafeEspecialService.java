package com.app.service;

import com.app.model.CafeEspecial;
import com.app.repository.CafeEspecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CafeEspecialService {

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

    /**
     * Cria um novo Café Especial.
     *
     * @param cafeEspecial o Café Especial a ser criado
     * @return o Café Especial criado
     */
    @Transactional
    public CafeEspecial criarCafeEspecial(CafeEspecial cafeEspecial) {
        return cafeEspecialRepository.save(cafeEspecial);
    }
}
