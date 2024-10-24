package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pagCartao")
public class PagCartaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "bandeira_cartao", nullable = false, length = 50)
    private String bandeiraCartao;

    @NotNull
    @Size(max = 16)
    @Column(name = "numero", nullable = false, length = 16)
    private String numero;

    @NotNull
    @Size(max = 5)
    @Column(name = "validade", nullable = false, length = 5)  // Formato MM/AA
    private String validade;

    @NotNull
    @Size(max = 100)
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotNull
    @Size(max = 11)
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "pagamento_id", nullable = false)
    private PagamentoModel pagamento;

    @Column(name = "autorizacao_cod", length = 100)
    private String autorizacaoCod;

    @NotNull
    @Column(name = "parcelas", nullable = false)
    private Integer parcelas;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(String bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;

    }

    public String getAutorizacaoCod() {
        return autorizacaoCod;
    }    

    public void setAutorizacaoCod(String autorizacaoCod) {        
        this.autorizacaoCod = autorizacaoCod;
    }

    public Integer getParcelas() {
        return parcelas;
    }    

    public void setParcelas(Integer parcelas) {        
        this.parcelas = parcelas;
    }    

    public PagamentoModel getPagamento() {        
        return pagamento;
    }    

    public void setPagamento(PagamentoModel pagamento) { 
        this.pagamento = pagamento;
    }   
    // (aqui, você já implementou os getters e setters)

    public void setPagamentoId(Long pagamentoId) {
        if (this.pagamento == null) {
            this.pagamento = new PagamentoModel();
        }
        this.pagamento.setId(pagamentoId);
    }
}
