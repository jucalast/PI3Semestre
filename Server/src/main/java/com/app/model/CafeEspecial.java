package com.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cafe_especial")
public class CafeEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Origem não pode ser nula")
    @Size(max = 100, message = "Origem não pode ter mais que 100 caracteres")
    @Column(name = "origem", nullable = false, length = 100)
    private String origem;

    @NotNull(message = "Variedade não pode ser nula")
    @Size(max = 100, message = "Variedade não pode ter mais que 100 caracteres")
    @Column(name = "variedade", nullable = false, length = 100)
    private String variedade;

    @NotNull(message = "Torrefação não pode ser nula")
    @Size(max = 50, message = "Torrefação não pode ter mais que 50 caracteres")
    @Column(name = "torrefacao", nullable = false, length = 50)
    private String torrefacao;

    @Size(max = 500, message = "Notas sensoriais não pode ter mais que 500 caracteres")
    @Column(name = "notas_sensoriais", length = 500)
    private String notasSensoriais;

    @NotNull(message = "Torra não pode ser nula")
    @Size(max = 50, message = "Torra não pode ter mais que 50 caracteres")
    @Column(name = "torra", nullable = false, length = 50)
    private String torra;

    @NotNull(message = "Beneficiamento não pode ser nulo")
    @Size(max = 100, message = "Beneficiamento não pode ter mais que 100 caracteres")
    @Column(name = "beneficiamento", nullable = false, length = 100)
    private String beneficiamento;

    @NotNull(message = "Data de Torra não pode ser nula")
    @Column(name = "data_torra", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataTorra;

    @NotNull(message = "Data de Validade não pode ser nula")
    @Column(name = "data_validade", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataValidade;

    @Size(max = 500, message = "Recomendações de preparo não pode ter mais que 500 caracteres")
    @Column(name = "recomendacoes_preparo", length = 500)
    private String recomendacoesPreparo;

    @OneToOne(mappedBy = "cafeEspecial", fetch = FetchType.LAZY)
    @JsonBackReference
    private Produto produto;
}
