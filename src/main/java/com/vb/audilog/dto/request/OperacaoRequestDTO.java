package com.vb.audilog.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vb.audilog.database.entity.Enuns.TipoOperacao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public record OperacaoRequestDTO(
    @NotNull(message = "O ID é obrigatório.")
    String id,
    @NotNull(message = "O tipo de operacao é obrigatória.")
    TipoOperacao tipo,
    @NotNull(message = "O valor é obrigatório.")
    @Positive(message = "O valor deve ser positivo.")
    BigDecimal valor,
    @NotNull(message = "A data e hora é obrigatória.")
    @PastOrPresent(message = "A data de contratação não pode ser no futuro.")
    LocalDateTime dataHora
) {
}
