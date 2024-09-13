package com.app.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Classe que representa um Café Especial.
 * Contém informações detalhadas sobre a origem, variedade, torrefação, notas
 * sensoriais, entre outras características do café.
 * Também possui um relacionamento muitos-para-muitos com a entidade Produto.
 */
@Entity
@Table(name = "cafe_especial")
public class CafeEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origem;
    private String variedade;
    private String torrefacao;
    private String notasSensoriais;
    private String torra;
    private String beneficiamento;
    private LocalDate dataTorra;
    private LocalDate dataValidade;
    private String recomendacoesPreparo;
    private String acidez;

    // Getters and Setters
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }

    public String getVariedade() { return variedade; }
    public void setVariedade(String variedade) { this.variedade = variedade; }

    public String getTorrefacao() { return torrefacao; }
    public void setTorrefacao(String torrefacao) { this.torrefacao = torrefacao; }

    public String getNotasSensoriais() { return notasSensoriais; }
    public void setNotasSensoriais(String notasSensoriais) { this.notasSensoriais = notasSensoriais; }

    public String getTorra() { return torra; }
    public void setTorra(String torra) { this.torra = torra; }

    public String getBeneficiamento() { return beneficiamento; }
    public void setBeneficiamento(String beneficiamento) { this.beneficiamento = beneficiamento; }

    public LocalDate getDataTorra() { return dataTorra; }
    public void setDataTorra(LocalDate dataTorra) { this.dataTorra = dataTorra; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public String getRecomendacoesPreparo() { return recomendacoesPreparo; }
    public void setRecomendacoesPreparo(String recomendacoesPreparo) { this.recomendacoesPreparo = recomendacoesPreparo; }

    public String getAcidez() { return acidez; }
    public void setAcidez(String acidez) { this.acidez = acidez; }

    @ManyToMany(mappedBy = "cafeEspeciais")
    private Set<Produto> produtos;


}
