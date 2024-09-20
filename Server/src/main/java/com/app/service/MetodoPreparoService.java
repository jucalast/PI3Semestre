package com.app.service;

import com.app.model.MetodoPreparo;
import com.app.repository.MetodoPreparoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável pela lógica de negócios para métodos de preparo de café.
 */
@Service
public class MetodoPreparoService {

    @Autowired
    private MetodoPreparoRepository metodoPreparoRepository;

    /**
     * Cria um novo método de preparo de café.
     *
     * @param metodoPreparo o método de preparo a ser criado
     * @return o método de preparo criado
     */
    @Transactional
    public MetodoPreparo criarMetodoPreparo(MetodoPreparo metodoPreparo) {
        return metodoPreparoRepository.save(metodoPreparo);
    }
}
