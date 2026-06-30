package com.vb.audilog.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.vb.audilog.dto.response.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {

        List<String> mensagens = ex.getBindingResult()
                                   .getFieldErrors()
                                   .stream()
                                   .map(erro -> erro.getDefaultMessage())
                                   .toList();

        ErrorResponseDTO response = new ErrorResponseDTO(
            LocalDateTime.now(),
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Erro de Validação",
            mensagens
        );

        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(response);
    }
}