package com.app.model;

import javax.persistence.*;
import java.math.BigDecimal; 
import java.time.LocalDateTime;

/**
 * A classe Pagamento mapeia a tabela "Pagamentos" no banco de dados.
 * @Entity indica que a classe é uma entidade JPA.
 * @Table(name = "Pagamentos") especifica o nome da tabela no banco de dados.
 */
@Entity
@Table(name = "Pagamentos")
public class Pagamento {

    /**
     * Campo identificador da entidade.
     * @Id indica que o campo é uma chave primária.
     * @GeneratedValue(strategy = GenerationType.IDENTITY) define que o valor do ID será gerado automaticamente pelo banco de dados.
     * @Column(name = "id", updatable = false, nullable = false) define que a coluna "id" não pode ser nula e não pode ser atualizada.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    /**
     * Relacionamento muitos-para-um com a entidade Pedido.
     * @ManyToOne indica que vários pagamentos podem estar associados a um único pedido.
     * @JoinColumn(name = "pedidoId", nullable = false) define a chave estrangeira "pedidoId" na tabela "Pagamentos".
     */
    @ManyToOne
    @JoinColumn(name = "pedidoId", nullable = false)
    private Pedido pedido;

    /**
     * Relacionamento muitos-para-um com a entidade MetodoPagamento.
     * @ManyToOne indica que vários pagamentos podem estar associados a um único método de pagamento.
     * @JoinColumn(name = "MetodoId", nullable = false) define a chave estrangeira "MetodoId" na tabela "Pagamentos".
     */
    @ManyToOne
    @JoinColumn(name = "MetodoId", nullable = false)
    private MetodosDePagamentos metodoPagamento;

    /**
     * Coluna que armazena a data e hora do pagamento.
     * @Column(name = "dataHora", nullable = false) define que a coluna "dataHora" não pode ser nula.
     * LocalDateTime é usado para armazenar a data e hora.
     */
    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    /**
     * Coluna que armazena o valor total do pagamento.
     * @Column(name = "total", nullable = false) define que a coluna "total" não pode ser nula.
     * BigDecimal é usado para garantir precisão em cálculos monetários.
     */
    @Column(name = "total", nullable = false)
    private BigDecimal total;

    /**
     * Coluna que armazena o status do pagamento.
     * @Column(name = "statusPagamento", nullable = false) define que a coluna "statusPagamento" não pode ser nula.
     * String é usada para armazenar o status, que pode ser 'pago', 'pendente' ou 'cancelado'.
     */
    @Column(name = "statusPagamento", nullable = false)
    private String statusPagamento;

    /**
     * Coluna que armazena o número de parcelas do pagamento.
     * @Column(name = "parcelas", nullable = false) define que a coluna "parcelas" não pode ser nula e tem um valor padrão de 1.
     */
    @Column(name = "parcelas", nullable = false)
    private Integer parcelas = 1;

    /**
     * Coluna que armazena o valor do desconto aplicado ao pagamento.
     * @Column(name = "desconto", nullable = false) define que a coluna "desconto" não pode ser nula e tem um valor padrão de 0.
     * BigDecimal é usado para garantir precisão em cálculos monetários.
     */
    @Column(name = "desconto", nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;

    // Getters e Setters

    /**
     * Retorna o ID do pagamento.
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do pagamento.
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o pedido associado ao pagamento.
     * @return pedido
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Define o pedido associado ao pagamento.
     * @param pedido
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Retorna o método de pagamento utilizado.
     * @return metodoPagamento
     */
    public MetodosDePagamentos getMetodoPagamento() {
        return metodoPagamento;
    }

    /**
     * Define o método de pagamento utilizado.
     * @param metodoPagamento
     */
    public void setMetodoPagamento(MetodosDePagamentos metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    /**
     * Retorna a data e hora do pagamento.
     * @return dataHora
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Define a data e hora do pagamento.
     * @param dataHora
     */
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * Retorna o valor total do pagamento.
     * @return total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Define o valor total do pagamento.
     * @param total
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Retorna o status do pagamento.
     * @return statusPagamento
     */
    public String getStatusPagamento() {
        return statusPagamento;
    }

    /**
     * Define o status do pagamento.
     * @param statusPagamento
     */
    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    /**
     * Retorna o número de parcelas do pagamento.
     * @return parcelas
     */
    public Integer getParcelas() {
        return parcelas;
    }

    /**
     * Define o número de parcelas do pagamento.
     * @param parcelas
     */
    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    /**
     * Retorna o valor do desconto aplicado ao pagamento.
     * @return desconto
     */
    public BigDecimal getDesconto() {
        return desconto;
    }

    /**
     * Define o valor do desconto aplicado ao pagamento.
     * @param desconto
     */
    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }
}
