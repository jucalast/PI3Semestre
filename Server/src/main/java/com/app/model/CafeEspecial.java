package com.seu_pacote.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cafe_especial")
public class CafeEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = true)
    private String aroma;

    @Column(nullable = true)
    private String sabor;

    @Column(nullable = true)
    private Integer pontuacao;

    @Column(nullable = true)
    private String origem;

    @Column(nullable = true)
    private String torrefacao;

    @Column(nullable = true)
    private String notasDegustacao;
}
