package com.app.model;

/* 
* Importa a classe BigDecimal para manipulação de valores decimais
* import java.math.BigDecimal;
* 
* Importa as anotações para mapeamento objeto-relacional
* import jakarta.persistence.*;
* 
* Importa a anotação @Data do Lombok para geração automática de métodos
* import lombok.Data;
*/
import java.math.BigDecimal;

import jakarta.persistence.*; 
import lombok.Data; 

/**
 * Entidade que representa a tabela 'pedidos' no banco de dados.
 * Esta classe armazena as informações relacionadas a um pedido,
 * como o usuário que fez o pedido, a data do pedido, status, endereço
 * de entrega e o total do pedido.
 * 
 * A anotação @Data da biblioteca Lombok é usada para gerar
 * automaticamente getters, setters e outros métodos comuns.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Entity
@Table(name = "pedidos")
public class PedidoModel {

    /**
     * ID do pedido. Este campo é a chave primária da tabela 'pedidos',
     * com geração automática de valor incremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID do usuário que fez o pedido. Este campo referencia a tabela 'usuarios',
     * e é obrigatório.
     */
    @Column(name = "usuarioId", nullable = false)
    private Integer usuarioId;

    /**
     * Data e hora em que o pedido foi realizado. Este campo é obrigatório.
     */
    @Column(name = "dataPedido", nullable = false)
    private String dataPedido;

    /**
     * Status do pedido. Este campo é obrigatório e armazena um valor inteiro
     * que representa o estado atual do pedido, utilizando um enum (1 a 5).
     * Os valores possíveis são:
     * 1 - Novo
     * 2 - Processando
     * 3 - Enviado
     * 4 - Entregue
     * 5 - Cancelado
     */
    @Column(name = "statusPedido", nullable = false)
    private Integer statusPedido;

    /**
     * Valor total do pedido, armazenado com precisão de 10 dígitos e 2 casas decimais.
     * Este campo é obrigatório.
     */
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    /**
     * Observações sobre o pedido, podendo incluir informações adicionais ou especiais
     * fornecidas pelo cliente. Este campo é opcional e tem um limite de 255 caracteres.
     */
    @Column(name = "observacoes", length = 255)
    private String observacoes;

    /**
     * Estado (UF) do endereço de entrega. Este campo é obrigatório e contém
     * dois caracteres que representam a sigla do estado (por exemplo, SP, RJ).
     */
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    /**
     * CEP (Código de Endereçamento Postal) do endereço de entrega.
     * Este campo é obrigatório e contém até 8 caracteres.
     */
    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    /**
     * Cidade do endereço de entrega. Este campo é obrigatório e contém até 100 caracteres.
     */
    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    /**
     * Bairro do endereço de entrega. Este campo é obrigatório e contém até 100 caracteres.
     */
    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    /**
     * Complemento do endereço de entrega. Este campo é opcional e contém até 100 caracteres.
     */
    @Column(name = "complemento", length = 100)
    private String complemento;

    /**
     * Número do endereço de entrega. Este campo é obrigatório.
     */
    @Column(name = "numero", nullable = false)
    private Integer numero;

    /**
     * Rua do endereço de entrega. Este campo é obrigatório e contém até 100 caracteres.
     */
    @Column(name = "rua", nullable = false, length = 100)
    private String rua;

    /**
     * Enum que representa o status do pedido.
     * Os valores possíveis são:
     * - NOVO (1)
     * - PROCESSANDO (2)
     * - ENVIADO (3)
     * - ENTREGUE (4)
     * - CANCELADO (5)
     */
    public enum StatusPedido {
        NOVO(1), PROCESSANDO(2), ENVIADO(3), ENTREGUE(4), CANCELADO(5);

        private final int code;

        StatusPedido(int code) {
            this.code = code;
        }

        /**
         * Retorna o código numérico correspondente ao status.
         * 
         * @return Código do status.
         */
        public int getCode() {
            return code;
        }

        /**
         * Converte um código numérico em um enum StatusPedido.
         * 
         * @param code Código do status.
         * @return Enum correspondente ao código.
         * @throws IllegalArgumentException Se o código não corresponder a nenhum status válido.
         */
        public static StatusPedido fromCode(int code) {
            for (StatusPedido status : StatusPedido.values()) {
                if (status.getCode() == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Status inválido: " + code);
        }
    }
}
