package com.vb.audilog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.audilog.Service.SistemaService;
import com.vb.audilog.dto.request.SistemaRequestDTO;
import com.vb.audilog.dto.response.SistemaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/lotes/analisar")
@RequiredArgsConstructor
public class SistemaController {

    private final SistemaService service;

    @PostMapping
    public ResponseEntity<SistemaResponseDTO> verificar(@Valid @RequestBody SistemaRequestDTO dto) {
        return ResponseEntity.ok()
                             .body(service.verificar(dto));
    }
}
