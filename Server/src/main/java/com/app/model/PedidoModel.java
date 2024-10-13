package com.app.model;
/**
 * Bibliotecas utilizadas:
 * java.time.LocalDateTime - usado para armazenar a data e hora do pedido.
 * java.math.BigDecimal - usado para armazenar o valor total do pedido.
 * javax.persistence.* - usado para mapear a entidade no banco de dados.
 */

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe PedidoModel representa a entidade 'pedidos' no banco de dados.
 * 
 * <p>Esta entidade corresponde à tabela 'pedidos' que contém informações sobre os pedidos
 * realizados pelos usuários na aplicação. A tabela contém atributos como ID, dados do
 * usuário, data do pedido, status, total e detalhes de entrega.
 * 
 * <p>Status do pedido:
 * <ul>
 *     <li>1 - Novo</li>
 *     <li>2 - Processando</li>
 *     <li>3 - Enviado</li>
 *     <li>4 - Entregue</li>
 *     <li>5 - Cancelado</li>
 * </ul>
 * 
 * @author Kairo
 * @version 2.0
 * @since 12-10-2024
 */
@Entity
@Table(name = "pedidos")
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    /**
     * Coluna id (chave primária).
     */
    
    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private UserModel usuario;

    /**
     * Coluna usuarioId, chave estrangeira que referencia a tabela 'usuarios'.
     */

    @Column(name = "dataPedido", nullable = false)
    private LocalDateTime dataPedido;

    /**
     * Coluna dataPedido, armazena a data e hora do pedido.
     */

    @Column(name = "statusPedido", nullable = false)
    private Integer statusPedido;

    /**
     * Coluna statusPedido, armazena o status do pedido.
     * Valores permitidos:
     * <ul>
     *     <li>1 - Novo</li>
     *     <li>2 - Processando</li>
     *     <li>3 - Enviado</li>
     *     <li>4 - Entregue</li>
     *     <li>5 - Cancelado</li>
     * </ul>
     */

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    /**
     * Coluna total, armazena o valor total do pedido.
     */

    @Column(name = "observacoes", length = 255)
    private String observacoes;

    /**
     * Coluna observacoes, armazena comentários adicionais sobre o pedido.
     */

    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    /**
     * Coluna estado, armazena a sigla do estado de entrega.
     */

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    /**
     * Coluna cep, armazena o CEP do endereço de entrega.
     */

    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    /**
     * Coluna cidade, armazena o nome da cidade de entrega.
     */

    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    /**
     * Coluna bairro, armazena o bairro de entrega.
     */

    @Column(name = "complemento", length = 100)
    private String complemento;

    /**
     * Coluna complemento, armazena o complemento do endereço de entrega.
     */

    @Column(name = "numero", nullable = false)
    private Integer numero;

    /**
     * Coluna numero, armazena o número do endereço de entrega.
     */

    @Column(name = "rua", nullable = false, length = 100)
    private String rua;

    /**
     * Coluna rua, armazena o nome da rua do endereço de entrega.
     */

    // Getters e Setters

    /**
     * Retorna o ID do pedido.
     * @return id do pedido
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do pedido.
     * @param id id do pedido
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o usuário relacionado ao pedido.
     * @return usuário
     */
    public UserModel getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário relacionado ao pedido.
     * @param usuario usuário do pedido
     */
    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna a data do pedido.
     * @return data do pedido
     */
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    /**
     * Define a data do pedido.
     * @param dataPedido data do pedido
     */
    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    /**
     * Retorna o status do pedido.
     * @return status do pedido
     */
    public Integer getStatusPedido() {
        return statusPedido;
    }

    /**
     * Define o status do pedido.
     * @param statusPedido status do pedido
     */
    public void setStatusPedido(Integer statusPedido) {
        this.statusPedido = statusPedido;
    }

    /**
     * Retorna o valor total do pedido.
     * @return total do pedido
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Define o valor total do pedido.
     * @param total valor total
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Retorna as observações do pedido.
     * @return observações
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * Define as observações do pedido.
     * @param observacoes observações
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * Retorna o estado de entrega.
     * @return estado de entrega
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define o estado de entrega.
     * @param estado estado de entrega
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Retorna o CEP de entrega.
     * @return CEP de entrega
     */
    public String getCep() {
        return cep;
    }

    /**
     * Define o CEP de entrega.
     * @param cep CEP de entrega
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * Retorna a cidade de entrega.
     * @return cidade de entrega
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Define a cidade de entrega.
     * @param cidade cidade de entrega
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Retorna o bairro de entrega.
     * @return bairro de entrega
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Define o bairro de entrega.
     * @param bairro bairro de entrega
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * Retorna o complemento do endereço.
     * @return complemento do endereço
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Define o complemento do endereço.
     * @param complemento complemento do endereço
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * Retorna o número do endereço de entrega.
     * @return número do endereço
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Define o número do endereço de entrega.
     * @param numero número do endereço
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * Retorna a rua do endereço de entrega.
     * @return rua do endereço
     */
    public String getRua() {
        return rua;
    }

    /**
     * Define a rua do endereço de entrega.
     * @param rua rua do endereço
     */
    public void setRua(String rua) {
        this.rua = rua;
    }
}
