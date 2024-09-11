package com.app.service;

import com.app.model.CafeEspecial;
import com.app.repository.CafeEspecialRepository;
import com.app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CafeEspecialService {

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Busca um Café Especial pelo ID
    public Optional<CafeEspecial> buscarPorId(Long id) {
        return cafeEspecialRepository.findById(id);
    }

    // Cria ou salva um Café Especial
    public CafeEspecial salvarCafeEspecial(CafeEspecial cafeEspecial) {
        // Verifica se o produto associado ao café especial existe
        if (cafeEspecial.getProduto() != null && produtoRepository.existsById(cafeEspecial.getProduto().getId())) {
            return cafeEspecialRepository.save(cafeEspecial);
        } else {
            throw new RuntimeException("Produto associado ao Café Especial não encontrado.");
        }
    }

    // Atualiza um Café Especial existente
    public CafeEspecial atualizarCafeEspecial(Long id, CafeEspecial cafeEspecialAtualizado) {
        return cafeEspecialRepository.findById(id).map(cafeEspecial -> {
            cafeEspecial.setAroma(cafeEspecialAtualizado.getAroma());
            cafeEspecial.setSabor(cafeEspecialAtualizado.getSabor());
            cafeEspecial.setPontuacao(cafeEspecialAtualizado.getPontuacao());
            cafeEspecial.setOrigem(cafeEspecialAtualizado.getOrigem());
            cafeEspecial.setTorrefacao(cafeEspecialAtualizado.getTorrefacao());
            cafeEspecial.setNotasDegustacao(cafeEspecialAtualizado.getNotasDegustacao());
            return cafeEspecialRepository.save(cafeEspecial);
        }).orElseThrow(() -> new RuntimeException("Café Especial não encontrado"));
    }

    // Deleta um Café Especial pelo ID
    public void deletarCafeEspecial(Long id) {
        cafeEspecialRepository.deleteById(id);
    }
}
