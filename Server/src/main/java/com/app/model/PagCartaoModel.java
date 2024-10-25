package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a entidade 'PagCartao' que armazena informações sobre pagamentos feitos com cartão.
 * Esta classe é mapeada para a tabela 'pagCartao' no banco de dados e inclui campos que
 * armazenam detalhes do cartão, informações do titular e do pagamento associado.
 * 
 * @author Kairo Chácara
 * @version 1.0
 * @since 2024-10-24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagCartao")
public class PagCartaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @NotBlank(message = "A bandeira do cartão é obrigatória.")
    @Size(max = 50)
    @Column(name = "bandeira_cartao", nullable = false, length = 50)
    private String bandeiraCartao;

    @NotBlank(message = "O número do cartão é obrigatório.")
    @Size(min = 16, max = 16)
    @Column(name = "numero", nullable = false, length = 16)
    private String numero;

    @NotBlank(message = "A validade do cartão é obrigatória.")
    @Size(min = 5, max = 5)
    @Column(name = "validade", nullable = false, length = 5)
    private String validade;

    @NotBlank(message = "O nome do titular do cartão é obrigatório.")
    @Size(max = 100)
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11)
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "pagamentoId")
    private PagamentoModel pagamento;

    @Column(name = "autorizacao_cod", length = 100)
    private String autorizacaoCod;

    @NotNull(message = "O número de parcelas é obrigatório.")
    @Column(name = "parcelas", nullable = false)
    private Integer parcelas;

    public void setPagamentoId(Long pagamentoId) {
        if (this.pagamento == null) {
            this.pagamento = new PagamentoModel();
        }
        this.pagamento.setId(pagamentoId);
    }
}
