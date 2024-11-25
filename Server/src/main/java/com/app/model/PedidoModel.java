package com.app.model;

import com.app.model.pagamento.PagamentoModel;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa um pedido feito por um usuário, armazenando detalhes do pedido e seu estado.
 */
@Data
@Entity
@Table(name = "pedidos")
public class PedidoModel {

    /**
     * Identificador único do pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O usuário que realizou o pedido.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel usuario;

    /**
     * Data e hora em que o pedido foi realizado.
     */
    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    /**
     * Status atual do pedido (e.g., 'Pendente', 'Concluído').
     */
    @Column(name = "status_pedido", nullable = false, length = 50)
    private String statusPedido;

    /**
     * Valor total do pedido.
     */
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    /**
     * Estado para entrega do pedido.
     */
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    /**
     * CEP para entrega do pedido.
     */
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    /**
     * Cidade para entrega do pedido.
     */
    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    /**
     * Bairro para entrega do pedido.
     */
    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    /**
     * Número da residência para entrega do pedido.
     */
    @Column(name = "numero", nullable = false)
    private Integer numero;

    /**
     * Rua para entrega do pedido.
     */
    @Column(name = "rua", nullable = false, length = 100)
    private String rua;

    /**
     * Complemento para a entrega do pedido, se aplicável.
     */
    @Column(name = "complemento", length = 100)
    private String complemento;

    /**
     * Observações adicionais sobre o pedido.
     */
    @Column(name = "observacoes", length = 255)
    private String observacoes;

    /**
     * Informações de pagamento associadas ao pedido.
     */
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private PagamentoModel pagamento;
}
