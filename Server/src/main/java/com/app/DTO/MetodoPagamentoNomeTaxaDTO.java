package com.app.DTO;

public class MetodoPagamentoNomeTaxaDTO {
    private String nome;
    private Float taxa;

    public MetodoPagamentoNomeTaxaDTO(String nome, Float taxa) {
        this.nome = nome;
        this.taxa = taxa;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getTaxa() {
        return taxa;
    }

    public void setTaxa(Float taxa) {
        this.taxa = taxa;
    }
}
