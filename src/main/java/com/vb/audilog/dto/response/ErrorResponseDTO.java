package com.vb.audilog.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
    LocalDateTime timestamp,
    Integer status,
    String Erro,
    List<String> mensagens
) {
}
