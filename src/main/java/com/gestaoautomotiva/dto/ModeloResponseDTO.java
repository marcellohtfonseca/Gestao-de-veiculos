package com.gestaoautomotiva.dto;

public class ModeloResponseDTO {

    private Long id;
    private String nome;
    private String categoria;
    private Long marcaId;
    private String marcaNome;

    public ModeloResponseDTO() {}

    public ModeloResponseDTO(Long id, String nome, String categoria, Long marcaId, String marcaNome) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.marcaId = marcaId;
        this.marcaNome = marcaNome;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Long getMarcaId() { return marcaId; }
    public void setMarcaId(Long marcaId) { this.marcaId = marcaId; }

    public String getMarcaNome() { return marcaNome; }
    public void setMarcaNome(String marcaNome) { this.marcaNome = marcaNome; }
}
