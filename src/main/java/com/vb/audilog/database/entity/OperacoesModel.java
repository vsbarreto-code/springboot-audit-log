package com.vb.audilog.database.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vb.audilog.database.entity.Enuns.TipoOperacao;

import lombok.Data;

@Data
public class OperacoesModel {
    private String id;
    private TipoOperacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;
}
