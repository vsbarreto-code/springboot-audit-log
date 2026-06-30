package com.vb.audilog.dto.request;

import java.util.List;

import com.vb.audilog.database.entity.OperacoesModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SistemaRequestDTO(
    @NotNull(message = "O nome do sistema de origem é obrigatório!")
    @Size(min = 3, max = 50, message = "O sistema deve ter entre 3 e 50 caracteres")
    String sistemaOrigem,
    @NotEmpty(message = "Informe pelo menos um item")
    List<@Valid OperacaoRequestDTO> operacoes
) {
}
