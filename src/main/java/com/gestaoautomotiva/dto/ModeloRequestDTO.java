package com.gestaoautomotiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ModeloRequestDTO {

    @NotBlank(message = "Nome do modelo é obrigatório")
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotNull(message = "ID da marca é obrigatório")
    private Long marcaId;

    public ModeloRequestDTO() {}

    public ModeloRequestDTO(String nome, String categoria, Long marcaId) {
        this.nome = nome;
        this.categoria = categoria;
        this.marcaId = marcaId;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Long getMarcaId() { return marcaId; }
    public void setMarcaId(Long marcaId) { this.marcaId = marcaId; }
}
