package com.gestaoautomotiva.controller;

import com.gestaoautomotiva.dto.VeiculoFiltroDTO;
import com.gestaoautomotiva.dto.VeiculoRequestDTO;
import com.gestaoautomotiva.dto.VeiculoResponseDTO;
import com.gestaoautomotiva.dto.VeiculoUpdateDTO;
import com.gestaoautomotiva.enums.StatusVeiculo;
import com.gestaoautomotiva.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/veiculos")
@Tag(name = "Veículos", description = "Gerenciamento de veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    @Operation(summary = "Listar veículos com filtros e paginação")
    public ResponseEntity<Page<VeiculoResponseDTO>> listar(
            @RequestParam(required = false) Long marcaId,
            @RequestParam(required = false) Long modeloId,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer anoMin,
            @RequestParam(required = false) Integer anoMax,
            @RequestParam(required = false) StatusVeiculo status,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        VeiculoFiltroDTO filtro = new VeiculoFiltroDTO(
                marcaId, modeloId, precoMin, precoMax, anoMin, anoMax, status);
        return ResponseEntity.ok(veiculoService.listarComFiltros(filtro, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veículo por ID")
    public ResponseEntity<VeiculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar veículo por placa")
    public ResponseEntity<VeiculoResponseDTO> buscarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(veiculoService.buscarPorPlaca(placa));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo veículo")
    public ResponseEntity<VeiculoResponseDTO> criar(@Valid @RequestBody VeiculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veículo completo")
    public ResponseEntity<VeiculoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoRequestDTO dto) {
        return ResponseEntity.ok(veiculoService.atualizar(id, dto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente (preço, quilometragem, status)")
    public ResponseEntity<VeiculoResponseDTO> atualizarParcial(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoUpdateDTO dto) {
        return ResponseEntity.ok(veiculoService.atualizarParcial(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover veículo")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veiculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
