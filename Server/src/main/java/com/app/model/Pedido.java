package com.app.model;

// Importa as anotações JPA necessárias e a classe LocalDateTime
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A classe Pedido representa a entidade "pedidos" no banco de dados.
 * Esta classe será mapeada para a tabela "pedidos".
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    /**
     * O campo 'id' é a chave primária da tabela 'pedidos'.
     * É um valor gerado automaticamente usando a estratégia de identidade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * O campo 'usuarioId' armazena o ID do usuário associado ao pedido.
     * É uma coluna obrigatória e faz referência à tabela 'usuarios'.
     */
    @Column(name = "usuarioId", nullable = false)
    private Integer usuarioId;

    /**
     * O campo 'dataPedido' armazena a data e hora em que o pedido foi feito.
     * É uma coluna obrigatória e recebe um valor padrão com a data/hora atual.
     */
    @Column(name = "dataPedido", nullable = false)
    private LocalDateTime dataPedido;

    /**
     * O campo 'statusPedido' armazena o status do pedido, como 'novo', 'processando', etc.
     * É uma coluna obrigatória e possui uma restrição para valores específicos.
     */
    @Column(name = "statusPedido", nullable = false)
    private String statusPedido;

    /**
     * O campo 'total' armazena o valor total do pedido.
     * É uma coluna obrigatória e possui um valor padrão de 0.
     */
    @Column(name = "total", nullable = false)
    private Float total;

    /**
     * O campo 'enderecoId' armazena o ID do endereço associado ao pedido.
     * É uma coluna obrigatória e faz referência à tabela 'enderecos'.
     */
    @Column(name = "enderecoId", nullable = false)
    private Integer enderecoId;

    /**
     * O campo 'observacoes' armazena quaisquer observações ou notas relacionadas ao pedido.
     * É uma coluna opcional com até 500 caracteres.
     */
    @Column(name = "observacoes")
    private String observacoes;

    /**
     * O campo 'dataAtualizacao' armazena a data e hora da última atualização do pedido.
     * Recebe um valor padrão com a data/hora atual.
     */
    @Column(name = "dataAtualizacao")
    private LocalDateTime dataAtualizacao;

    /**
     * Construtor padrão. É necessário para a JPA.
     * Ele será usado para criar instâncias de Pedido sem definir valores iniciais.
     */
    public Pedido() {
        // Construtor padrão vazio
    }

    // Getters e Setters para cada campo da entidade

    /**
     * Obtém o ID do pedido.
     * @return id do pedido
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do pedido.
     * @param id o novo ID do pedido
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o ID do usuário associado ao pedido.
     * @return id do usuário
     */
    public Integer getUsuarioId() {
        return usuarioId;
    }

    /**
     * Define o ID do usuário associado ao pedido.
     * @param usuarioId o novo ID do usuário
     */
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Obtém a data e hora em que o pedido foi feito.
     * @return data e hora do pedido
     */
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    /**
     * Define a data e hora do pedido.
     * @param dataPedido a nova data e hora do pedido
     */
    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    /**
     * Obtém o status do pedido.
     * @return status do pedido
     */
    public String getStatusPedido() {
        return statusPedido;
    }

    /**
     * Define o status do pedido.
     * @param statusPedido o novo status do pedido
     */
    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    /**
     * Obtém o valor total do pedido.
     * @return valor total do pedido
     */
    public Float getTotal() {
        return total;
    }

    /**
     * Define o valor total do pedido.
     * @param total o novo valor total do pedido
     */
    public void setTotal(Float total) {
        this.total = total;
    }

    /**
     * Obtém o ID do endereço associado ao pedido.
     * @return id do endereço
     */
    public Integer getEnderecoId() {
        return enderecoId;
    }

    /**
     * Define o ID do endereço associado ao pedido.
     * @param enderecoId o novo ID do endereço
     */
    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }

    /**
     * Obtém as observações do pedido.
     * @return observações do pedido
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * Define as observações do pedido.
     * @param observacoes as novas observações do pedido
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * Obtém a data e hora da última atualização do pedido.
     * @return data e hora da última atualização
     */
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * Define a data e hora da última atualização do pedido.
     * @param dataAtualizacao a nova data e hora da atualização
     */
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
