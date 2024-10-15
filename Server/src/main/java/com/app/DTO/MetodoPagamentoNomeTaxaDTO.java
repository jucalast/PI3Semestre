package com.app.DTO;

/**
 * Classe Data Transfer Object (DTO) para representar os dados de um método de pagamento,
 * contendo o nome e a taxa associada.
 * 
 * Esta classe é utilizada para transferir dados entre diferentes camadas da aplicação, 
 * especialmente ao retornar informações sobre métodos de pagamento de forma simplificada.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-14
 */
public class MetodoPagamentoNomeTaxaDTO {
    
    private String nome; // Nome do método de pagamento
    private Float taxa;  // Taxa associada ao método de pagamento

    /**
     * Construtor da classe MetodoPagamentoNomeTaxaDTO.
     *
     * @param nome O nome do método de pagamento.
     * @param taxa A taxa associada ao método de pagamento.
     */
    public MetodoPagamentoNomeTaxaDTO(String nome, Float taxa) {
        this.nome = nome;
        this.taxa = taxa;
    }

    // Getters e Setters

    /**
     * Obtém o nome do método de pagamento.
     *
     * @return O nome do método de pagamento.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do método de pagamento.
     *
     * @param nome O nome a ser definido para o método de pagamento.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a taxa associada ao método de pagamento.
     *
     * @return A taxa do método de pagamento.
     */
    public Float getTaxa() {
        return taxa;
    }

    /**
     * Define a taxa associada ao método de pagamento.
     *
     * @param taxa A taxa a ser definida para o método de pagamento.
     */
    public void setTaxa(Float taxa) {
        this.taxa = taxa;
    }
}
