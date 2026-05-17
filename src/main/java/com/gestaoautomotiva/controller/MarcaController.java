package com.gestaoautomotiva.controller;

import com.gestaoautomotiva.dto.MarcaRequestDTO;
import com.gestaoautomotiva.dto.MarcaResponseDTO;
import com.gestaoautomotiva.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@Tag(name = "Marcas", description = "Gerenciamento de marcas de veículos")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as marcas")
    public ResponseEntity<List<MarcaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(marcaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar marca por ID")
    public ResponseEntity<MarcaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova marca")
    public ResponseEntity<MarcaResponseDTO> criar(@Valid @RequestBody MarcaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar marca")
    public ResponseEntity<MarcaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MarcaRequestDTO dto) {
        return ResponseEntity.ok(marcaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover marca")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        marcaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
