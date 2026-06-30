package com.vb.audilog.database.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class SistemaModel {
    private String sistemaOrigem;
    private List<OperacoesModel> operacoes;

    private Integer totalOperacoes;
    private BigDecimal totalCreditos;
    private BigDecimal totalDebitos;
    private BigDecimal saldoFinal;
    private List<String> idOperacoes;
    private Boolean possuiInconsistencia;


}
