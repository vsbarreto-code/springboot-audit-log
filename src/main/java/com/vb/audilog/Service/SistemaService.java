package com.vb.audilog.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.vb.audilog.database.entity.Enuns.TipoOperacao;
import com.vb.audilog.database.entity.OperacoesModel;
import com.vb.audilog.database.entity.SistemaModel;
import com.vb.audilog.dto.request.SistemaRequestDTO;
import com.vb.audilog.dto.response.SistemaResponseDTO;
import com.vb.audilog.mapper.ISistemaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SistemaService {

    private final ISistemaMapper mapper;

    //  POST
    public SistemaResponseDTO verificar(SistemaRequestDTO dto) {

        SistemaModel sistemaModel = mapper.toEntity(dto);

        List<OperacoesModel> operacoes = sistemaModel.getOperacoes();

        int totalOperacoes = operacoes.size();

        Map<String, Long> quantidadePorId = operacoes.stream()
                                                     .collect(Collectors.groupingBy(
                                                         OperacoesModel::getId,
                                                         Collectors.counting()
                                                     ));

        List<String> idOperacoes = operacoes.stream()
                                            .filter(op ->
                                                        op.getValor().compareTo(BigDecimal.valueOf(50000)) > 0
                                                            || (op.getTipo() == TipoOperacao.DEBITO
                                                            && op.getValor().compareTo(BigDecimal.valueOf(10000)) > 0)
                                                            || quantidadePorId.get(op.getId()) > 1
                                            )
                                            .map(OperacoesModel::getId)
                                            .distinct()
                                            .toList();

        BigDecimal totalCreditos = operacoes.stream()
                                            .filter(op -> op.getTipo() == TipoOperacao.CREDITO)
                                            .map(OperacoesModel::getValor)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebitos = operacoes.stream()
                                           .filter(op -> op.getTipo() == TipoOperacao.DEBITO)
                                           .map(OperacoesModel::getValor)
                                           .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoFinal = totalCreditos.subtract(totalDebitos);

        boolean possuiInconsistencia = !idOperacoes.isEmpty()
            || saldoFinal.compareTo(BigDecimal.ZERO) < 0;

        sistemaModel.setTotalOperacoes(totalOperacoes);
        sistemaModel.setTotalCreditos(totalCreditos);
        sistemaModel.setTotalDebitos(totalDebitos);
        sistemaModel.setSaldoFinal(saldoFinal);
        sistemaModel.setIdOperacoes(idOperacoes);
        sistemaModel.setPossuiInconsistencia(possuiInconsistencia);

        return mapper.toResponse(sistemaModel);
    }
}
