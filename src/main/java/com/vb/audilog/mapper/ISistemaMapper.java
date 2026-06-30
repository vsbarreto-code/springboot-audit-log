package com.vb.audilog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.audilog.Service.SistemaService;
import com.vb.audilog.database.entity.OperacoesModel;
import com.vb.audilog.database.entity.SistemaModel;
import com.vb.audilog.dto.request.OperacaoRequestDTO;
import com.vb.audilog.dto.request.SistemaRequestDTO;
import com.vb.audilog.dto.response.SistemaResponseDTO;

@Mapper(componentModel = "spring")
public interface ISistemaMapper {
    @Mapping(target = "totalOperacoes", ignore = true)
    @Mapping(target = "totalCreditos", ignore = true)
    @Mapping(target = "totalDebitos", ignore = true)
    @Mapping(target = "saldoFinal", ignore = true)
    @Mapping(target = "idOperacoes", ignore = true)
    @Mapping(target = "possuiInconsistencia", ignore = true)
    SistemaModel toEntity(SistemaRequestDTO dto);

    SistemaResponseDTO toResponse(SistemaModel model);

    OperacoesModel toOperacaoModel(OperacaoRequestDTO dto);

}
