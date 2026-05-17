package com.gestaoautomotiva.controller;

import com.gestaoautomotiva.dto.ModeloRequestDTO;
import com.gestaoautomotiva.dto.ModeloResponseDTO;
import com.gestaoautomotiva.service.ModeloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@Tag(name = "Modelos", description = "Gerenciamento de modelos de veículos")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os modelos")
    public ResponseEntity<List<ModeloResponseDTO>> listarTodos() {
        return ResponseEntity.ok(modeloService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar modelo por ID")
    public ResponseEntity<ModeloResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.buscarPorId(id));
    }

    @GetMapping("/marca/{marcaId}")
    @Operation(summary = "Listar modelos por marca")
    public ResponseEntity<List<ModeloResponseDTO>> listarPorMarca(@PathVariable Long marcaId) {
        return ResponseEntity.ok(modeloService.listarPorMarca(marcaId));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo modelo")
    public ResponseEntity<ModeloResponseDTO> criar(@Valid @RequestBody ModeloRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar modelo")
    public ResponseEntity<ModeloResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ModeloRequestDTO dto) {
        return ResponseEntity.ok(modeloService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover modelo")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        modeloService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
