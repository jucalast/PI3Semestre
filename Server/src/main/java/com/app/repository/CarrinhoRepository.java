package com.app.repository;

import com.app.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface responsável por realizar aperações de persistência relacionadas à entidade carrinho.
 *
 * @author Ricardo L. Ferreira
 * */
@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    /**
     * Busca um usuário no banco de dados a partir do id.
     * @param idUser O id do usuário.
     * @return Retorna uma lista genérica instânciada da classe UserModel.
     */
    List<Carrinho> findByUserModelId(Long idUser);

    /**
     * Método que verifica a existência de um registro especifico que contenha o id do usuário
     * e o id do produto.
     * @param idUser O id do usuário.
     * @param productId O id do produto.
     * Return Retorna true caso exista um registro que contenha ambos os ids ou false, caso não
     */
    boolean existsByUserModelIdAndProdutoId(Long idUser, Long productId);

    /**
     * Método que busca um registro especifico que contenha o id do usuário e o id do produto.
     * A presença do Optional evita problemas com NullPointerException, já que há a chance de que
     * a instancia da classe não exista.
     * @param userModelId O id do usuário.
     * @param produtoId O id do produto.
     * @return O retorno do método é uma instância da classe encapsulada, caso ela exista.
     */
    Optional<Carrinho> findByUserModelIdAndProdutoId(Long userModelId, Long produtoId);

    /**
     * Método responsável por eliminar o registro no banco de dados que contenha o
     * id do usuário e o id do produto.
     * @param idUser O id do usuário.
     * @param productId O id do produto.
     */
    void deleteByUserModelIdAndProdutoId(Long idUser, Long productId);
}

