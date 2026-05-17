package com.gestaoautomotiva.dto;

import jakarta.validation.constraints.NotBlank;

public class MarcaRequestDTO {

    @NotBlank(message = "Nome da marca é obrigatório")
    private String nome;

    @NotBlank(message = "País de origem é obrigatório")
    private String paisOrigem;

    public MarcaRequestDTO() {}

    public MarcaRequestDTO(String nome, String paisOrigem) {
        this.nome = nome;
        this.paisOrigem = paisOrigem;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPaisOrigem() { return paisOrigem; }
    public void setPaisOrigem(String paisOrigem) { this.paisOrigem = paisOrigem; }
}
