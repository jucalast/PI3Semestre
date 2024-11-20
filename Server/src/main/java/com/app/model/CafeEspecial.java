package com.app.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Representa um Café Especial com atributos específicos, como origem,
 * variedade, torra e notas sensoriais.
 *
 * A entidade está vinculada ao produto ao qual pertence e contém informações
 * detalhadas para categorização e recomendação de preparo.
 *
 * @version 1.0
 * @since 2024-10-05
 */
@Data
@Entity
@Table(name = "cafe_especial")
public class CafeEspecial {

    /**
     * Identificador único para o Café Especial
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Origem do café, ex: país ou região.
     */
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String origem;

    /**
     * Variedade do café, ex: Arábica, Robusta.
     */
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String variedade;

    /**
     * Torrefação realizada no café, ex: artesanal.
     */
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String torrefacao;

    /**
     * Notas sensoriais que descrevem o sabor do café, ex: frutado, floral.
     */
    private String notasSensoriais;

    /**
     * Tipo de torra do café, ex: clara, média, escura.
     */
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String torra;

    /**
     * Método de beneficiamento do café, ex: natural, lavado.
     */
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String beneficiamento;

    /**
     * Data em que o café foi torrado.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "data_torra")
    @Temporal(TemporalType.DATE)
    private Date dataTorra;

    /**
     * Data de validade do café.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "data_validade")
    @Temporal(TemporalType.DATE)
    private Date dataValidade;

    /**
     * Recomendações para o preparo do café, ex: temperatura, tempo de infusão.
     */
    @Column(name = "recomendacoes_preparo")
    private String recomendacoesPreparo;

    /**
     * Relacionamento com o Produto ao qual o café especial pertence.
     */
    @OneToOne
    @JoinColumn(name = "produto_id")
    @JsonBackReference // Para evitar loop de serialização
    private Produto produto;

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "CafeEspecial{" +
                "id=" + id +
                ", notasSensoriais='" + notasSensoriais + '\'' +
                ", origem='" + origem + '\'' +
                ", variedade='" + variedade + '\'' +
                ", torrefacao='" + torrefacao + '\'' +
                ", torra='" + torra + '\'' +
                ", beneficiamento='" + beneficiamento + '\'' +
                ", dataTorra=" + dataTorra +
                ", dataValidade=" + dataValidade +
                ", recomendacoesPreparo='" + recomendacoesPreparo + '\'' +
                // Evitar chamada recursiva
                ", produto=" + (produto != null ? produto.getId() : null) +
                '}';
    }
}
