package com.vb.audilog.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record SistemaResponseDTO(
    Integer totalOperacoes,
    BigDecimal totalCreditos,
    BigDecimal totalDebitos,
    BigDecimal saldoFinal,
    List<String> idOperacoes,
    Boolean possuiInconsistencia
) {
}
