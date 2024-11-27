package com.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exceptions.ValidationUtil;
import com.app.model.CafeEspecial;
import com.app.model.MetodoPreparo;
import com.app.model.Produto;
import com.app.repository.CafeEspecialRepository;
import com.app.repository.MetodoPreparoRepository;
import com.app.repository.ProdutoRepository;

/**
 * Serviço para operações relacionadas a produtos, incluindo CRUD e filtragem de
 * atributos.
 */
@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CafeEspecialRepository cafeEspecialRepository;

    @Autowired
    private MetodoPreparoRepository metodoPreparoRepository;

    /**
     * Cria um novo produto no sistema após validação.
     *
     * @param produto Objeto Produto a ser criado.
     * @return Produto criado.
     * @throws RuntimeException se já existe um produto com o mesmo nome.
     */
    @Transactional
    public Produto createProduto(Produto produto) {
        logger.info("Criando um novo produto: {}", produto.getNome());

        ValidationUtil.validarObjeto(produto);

        if (produtoRepository.existsByNome(produto.getNome())) {
            logger.error("Erro: Já existe um produto com o nome {}", produto.getNome());
            throw new RuntimeException("Já existe um produto com o nome: " + produto.getNome());
        }

        Produto savedProduto = produtoRepository.save(produto);
        logger.info("Produto {} criado com sucesso", savedProduto.getNome());
        return savedProduto;
    }

    /**
     * Retorna uma lista de todos os produtos.
     *
     * @return Lista de todos os produtos.
     */
    public List<Produto> getAllProdutos() {
        logger.info("Listando todos os produtos");
        List<Produto> produtos = produtoRepository.findAll();

        produtos.forEach(produto -> {
            Hibernate.initialize(produto.getCafeEspecial());
            Hibernate.initialize(produto.getMetodoPreparo());
        });

        return produtos;
    }

    /**
     * Retorna uma lista de todos os produtos com suas especializações
     * carregadas.
     *
     * @return Lista de produtos com especializações.
     */
    public List<Produto> getAllProdutosComEspecializacoes() {
        logger.info("Listando todos os produtos com suas especializações");
        List<Produto> produtos = produtoRepository.findAll();
        return produtos;
    }

    /**
     * Busca um produto pelo ID.
     *
     * @param id ID do produto a ser buscado.
     * @return Produto encontrado.
     * @throws RuntimeException se o produto com o ID especificado não for
     * encontrado.
     */
    public Produto getProdutoById(Long id) {
        logger.info("Buscando produto com ID: {}", id);
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Produto não encontrado com ID: {}", id);
                    return new RuntimeException("Produto não encontrado com ID: " + id);
                });

        Hibernate.initialize(produto.getCafeEspecial());
        Hibernate.initialize(produto.getMetodoPreparo());

        return produto;
    }

    public Produto getProdutoSemEspecializacoesById(Long id) {
        logger.info("Buscando produto sem especializações com ID: {}", id);
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Produto não encontrado com ID: {}", id);
                    return new RuntimeException("Produto não encontrado com ID: " + id);
                });

        return produto;
    }

    /**
     * Atualiza um produto existente.
     *
     * @param id ID do produto a ser atualizado.
     * @param produto Dados atualizados do produto.
     * @return Produto atualizado.
     * @throws RuntimeException se o produto não for encontrado ou se já existir
     * um produto com o novo nome.
     */
    @Transactional
    public Produto updateProduto(Long id, Produto produto) {
        logger.info("Atualizando produto com ID: {}", id);

        ValidationUtil.validarObjeto(produto);

        Produto existingProduto = getProdutoById(id);

        // Log dos valores recebidos
        logger.info("Valores recebidos para atualização: Nome: {}, Descrição: {}, Preço: {}, Quantidade Estoque: {}, Avaliação: {}",
                produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQuantidadeEstoque(), produto.getAvaliacao());

        if (produto.getCafeEspecial() != null) {
            CafeEspecial cafeEspecial = produto.getCafeEspecial();
            logger.info("Valores de CafeEspecial recebidos: Notas Sensoriais: {}, Origem: {}, Variedade: {}, Torrefação: {}, Torra: {}, Beneficiamento: {}, Data Torra: {}, Data Validade: {}, Recomendações Preparo: {}",
                    cafeEspecial.getNotasSensoriais(), cafeEspecial.getOrigem(), cafeEspecial.getVariedade(), cafeEspecial.getTorrefacao(), cafeEspecial.getTorra(), cafeEspecial.getBeneficiamento(), cafeEspecial.getDataTorra(), cafeEspecial.getDataValidade(), cafeEspecial.getRecomendacoesPreparo());
        }

        // Atualiza os dados do produto
        existingProduto.setNome(produto.getNome());
        existingProduto.setDescricao(produto.getDescricao());
        existingProduto.setPreco(produto.getPreco());
        existingProduto.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        existingProduto.setAvaliacao(produto.getAvaliacao());

        // Atualiza as imagens do produto, permitindo remoção e adição
        if (produto.getImagens() != null) {
            existingProduto.setImagens(produto.getImagens());
        }

        // Atualiza a especialização CafeEspecial se existir
        if (produto.getCafeEspecial() != null) {
            if (existingProduto.getCafeEspecial() == null) {
                existingProduto.setCafeEspecial(produto.getCafeEspecial());
                produto.getCafeEspecial().setProduto(existingProduto);
            } else {
                CafeEspecial cafeEspecial = produto.getCafeEspecial();
                if (cafeEspecial.getNotasSensoriais() != null) {
                    existingProduto.getCafeEspecial().setNotasSensoriais(cafeEspecial.getNotasSensoriais());
                }
                if (cafeEspecial.getOrigem() != null) {
                    existingProduto.getCafeEspecial().setOrigem(cafeEspecial.getOrigem());
                }
                if (cafeEspecial.getVariedade() != null) {
                    existingProduto.getCafeEspecial().setVariedade(cafeEspecial.getVariedade());
                }
                if (cafeEspecial.getTorrefacao() != null) {
                    existingProduto.getCafeEspecial().setTorrefacao(cafeEspecial.getTorrefacao());
                }
                if (cafeEspecial.getTorra() != null) {
                    existingProduto.getCafeEspecial().setTorra(cafeEspecial.getTorra());
                }
                if (cafeEspecial.getBeneficiamento() != null) {
                    existingProduto.getCafeEspecial().setBeneficiamento(cafeEspecial.getBeneficiamento());
                }
                if (cafeEspecial.getDataTorra() != null) {
                    existingProduto.getCafeEspecial().setDataTorra(cafeEspecial.getDataTorra());
                }
                if (cafeEspecial.getDataValidade() != null) {
                    existingProduto.getCafeEspecial().setDataValidade(cafeEspecial.getDataValidade());
                }
                if (cafeEspecial.getRecomendacoesPreparo() != null) {
                    existingProduto.getCafeEspecial().setRecomendacoesPreparo(cafeEspecial.getRecomendacoesPreparo());
                }
            }
        }

        // Atualiza a especialização MetodoPreparo se existir
        if (produto.getMetodoPreparo() != null) {
            if (existingProduto.getMetodoPreparo() == null) {
                existingProduto.setMetodoPreparo(produto.getMetodoPreparo());
                produto.getMetodoPreparo().setProduto(existingProduto);
            } else {
                existingProduto.getMetodoPreparo().setComplexidade(produto.getMetodoPreparo().getComplexidade());
                // Atualizar outros campos conforme necessário...
            }
        }

        Produto updatedProduto = produtoRepository.save(existingProduto);
        logger.info("Produto {} atualizado com sucesso", updatedProduto.getNome());
        return updatedProduto;
    }

    @Transactional
    public void bulkUpdateProdutos(List<Produto> produtos) {
        for (Produto produto : produtos) {
            Produto existingProduto = getProdutoById(produto.getId());
            existingProduto.setPreco(produto.getPreco());
            produtoRepository.save(existingProduto);
        }
    }

    /**
     * Deleta um produto pelo ID.
     *
     * @param id ID do produto a ser deletado.
     * @throws RuntimeException se o produto com o ID especificado não for
     * encontrado.
     */
    @Transactional
    public void deleteProduto(Long id) {
        logger.info("Deletando produto com ID: {}", id);

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        // Verifica e deleta a especialização CafeEspecial se existir
        if (produto.getCafeEspecial() != null) {
            cafeEspecialRepository.delete(produto.getCafeEspecial());
            logger.info("Especialização CafeEspecial deletada para o produto ID: {}", id);
        }

        // Verifica e deleta a especialização MetodoPreparo se existir
        if (produto.getMetodoPreparo() != null) {
            metodoPreparoRepository.delete(produto.getMetodoPreparo());
            logger.info("Especialização MetodoPreparo deletada para o produto ID: {}", id);
        }

        // Após deletar as especializações, deleta o próprio produto
        produtoRepository.delete(produto);
        logger.info("Produto com ID {} deletado com sucesso", id);
    }

    /**
     * Desativa um produto pelo ID.
     *
     * @param id ID do produto a ser desativado.
     * @return Produto desativado.
     * @throws RuntimeException se o produto com o ID especificado não for
     * encontrado.
     */
    @Transactional
    public Produto deactivateProduto(Long id) {
        logger.info("Desativando produto com ID: {}", id);
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        logger.info("Produto encontrado: {}", produto);
        produto.setAtivo(false);
        Produto updatedProduto = produtoRepository.save(produto);
        logger.info("Produto com ID {} desativado com sucesso", id);
        return updatedProduto;
    }

    /**
     * Ativa um produto pelo ID.
     *
     * @param id ID do produto a ser ativado.
     * @return Produto ativado.
     * @throws RuntimeException se o produto com o ID especificado não for
     * encontrado.
     */
    @Transactional
    public Produto activateProduto(Long id) {
        logger.info("Ativando produto com ID: {}", id);
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        logger.info("Produto encontrado: {}", produto);
        produto.setAtivo(true);
        Produto updatedProduto = produtoRepository.save(produto);
        logger.info("Produto com ID {} ativado com sucesso", id);
        return updatedProduto;
    }

    /**
     * Lista atributos dos produtos, incluindo especializações de CafeEspecial e
     * MetodoPreparo.
     *
     * @return Lista de atributos de produtos.
     */
    public List<Map<String, Object>> listarAtributos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<Map<String, Object>> listaAtributos = new ArrayList<>();

        for (Produto produto : produtos) {
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("avaliacao", produto.getAvaliacao());

            CafeEspecial cafeEspecial = produto.getCafeEspecial();
            if (cafeEspecial != null) {
                atributos.put("notasSensoriais", cafeEspecial.getNotasSensoriais());
                atributos.put("origem", cafeEspecial.getOrigem());
                atributos.put("recomendacoesPreparo", cafeEspecial.getRecomendacoesPreparo());
                atributos.put("torra", cafeEspecial.getTorra());
                atributos.put("torrefacao", cafeEspecial.getTorrefacao());
                atributos.put("variedade", cafeEspecial.getVariedade());
            }

            MetodoPreparo metodoPreparo = produto.getMetodoPreparo();
            if (metodoPreparo != null) {
                atributos.put("complexidade", metodoPreparo.getComplexidade());
                atributos.put("marca", metodoPreparo.getMarca());
                atributos.put("material", metodoPreparo.getMaterial());
                atributos.put("tipoPreparo", metodoPreparo.getTipoPreparo());
            }

            listaAtributos.add(atributos);
        }

        return listaAtributos;
    }

    /**
     * Busca produtos que atendem aos critérios de atributos especificados.
     *
     * @param atributos Mapa de atributos a serem buscados.
     * @return Lista de produtos que atendem aos critérios.
     */
    public List<Produto> buscarProdutosPorAtributos(Map<String, String> atributos) {
        logger.info("Buscando produtos com os atributos {}", atributos);
        List<Produto> produtos = produtoRepository.findAll();
        List<Produto> produtosFiltrados = new ArrayList<>();

        Map<String, BiPredicate<CafeEspecial, String>> cafeEspecialChecks = new HashMap<>();
        cafeEspecialChecks.put("variedade", (cafeEspecial, v) -> cafeEspecial.getVariedade() != null && cafeEspecial.getVariedade().equalsIgnoreCase(v));
        cafeEspecialChecks.put("torra", (cafeEspecial, v) -> cafeEspecial.getTorra() != null && cafeEspecial.getTorra().equalsIgnoreCase(v));
        cafeEspecialChecks.put("origem", (cafeEspecial, v) -> cafeEspecial.getOrigem() != null && cafeEspecial.getOrigem().equalsIgnoreCase(v));
        cafeEspecialChecks.put("notassensoriais", (cafeEspecial, v) -> cafeEspecial.getNotasSensoriais() != null && cafeEspecial.getNotasSensoriais().equalsIgnoreCase(v));
        cafeEspecialChecks.put("recomendacoespreparo", (cafeEspecial, v) -> cafeEspecial.getRecomendacoesPreparo() != null && cafeEspecial.getRecomendacoesPreparo().equalsIgnoreCase(v));
        cafeEspecialChecks.put("torrefacao", (cafeEspecial, v) -> cafeEspecial.getTorrefacao() != null && cafeEspecial.getTorrefacao().equalsIgnoreCase(v));

        Map<String, BiPredicate<MetodoPreparo, String>> metodoPreparoChecks = new HashMap<>();
        metodoPreparoChecks.put("marca", (metodoPreparo, v) -> metodoPreparo.getMarca() != null && metodoPreparo.getMarca().equalsIgnoreCase(v));
        metodoPreparoChecks.put("complexidade", (metodoPreparo, v) -> metodoPreparo.getComplexidade() != null && metodoPreparo.getComplexidade().toString().equalsIgnoreCase(v));
        metodoPreparoChecks.put("material", (metodoPreparo, v) -> metodoPreparo.getMaterial() != null && metodoPreparo.getMaterial().equalsIgnoreCase(v));
        metodoPreparoChecks.put("tipopreparo", (metodoPreparo, v) -> metodoPreparo.getTipoPreparo() != null && metodoPreparo.getTipoPreparo().equalsIgnoreCase(v));

        for (Produto produto : produtos) {
            CafeEspecial cafeEspecial = produto.getCafeEspecial();
            MetodoPreparo metodoPreparo = produto.getMetodoPreparo();

            boolean matches = true;
            for (Map.Entry<String, String> entry : atributos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (cafeEspecialChecks.containsKey(key) && cafeEspecial != null) {
                    matches = matches && cafeEspecialChecks.get(key).test(cafeEspecial, value);
                } else if (metodoPreparoChecks.containsKey(key) && metodoPreparo != null) {
                    matches = matches && metodoPreparoChecks.get(key).test(metodoPreparo, value);
                } else {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                produtosFiltrados.add(produto);
            }
        }

        return produtosFiltrados;
    }
}
