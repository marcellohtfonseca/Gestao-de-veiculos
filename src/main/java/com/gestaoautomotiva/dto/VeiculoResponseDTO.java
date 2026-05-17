package com.gestaoautomotiva.dto;

import com.gestaoautomotiva.enums.StatusVeiculo;

import java.math.BigDecimal;

public class VeiculoResponseDTO {

    private Long id;
    private String cor;
    private Integer ano;
    private BigDecimal preco;
    private Integer quilometragem;
    private StatusVeiculo status;
    private String placa;
    private String chassi;
    private Long modeloId;
    private String modeloNome;
    private String modeloCategoria;
    private Long marcaId;
    private String marcaNome;
    private String marcaPaisOrigem;

    public VeiculoResponseDTO() {}

    public VeiculoResponseDTO(Long id, String cor, Integer ano, BigDecimal preco,
                               Integer quilometragem, StatusVeiculo status, String placa,
                               String chassi, Long modeloId, String modeloNome,
                               String modeloCategoria, Long marcaId, String marcaNome,
                               String marcaPaisOrigem) {
        this.id = id;
        this.cor = cor;
        this.ano = ano;
        this.preco = preco;
        this.quilometragem = quilometragem;
        this.status = status;
        this.placa = placa;
        this.chassi = chassi;
        this.modeloId = modeloId;
        this.modeloNome = modeloNome;
        this.modeloCategoria = modeloCategoria;
        this.marcaId = marcaId;
        this.marcaNome = marcaNome;
        this.marcaPaisOrigem = marcaPaisOrigem;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuilometragem() { return quilometragem; }
    public void setQuilometragem(Integer quilometragem) { this.quilometragem = quilometragem; }

    public StatusVeiculo getStatus() { return status; }
    public void setStatus(StatusVeiculo status) { this.status = status; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }

    public Long getModeloId() { return modeloId; }
    public void setModeloId(Long modeloId) { this.modeloId = modeloId; }

    public String getModeloNome() { return modeloNome; }
    public void setModeloNome(String modeloNome) { this.modeloNome = modeloNome; }

    public String getModeloCategoria() { return modeloCategoria; }
    public void setModeloCategoria(String modeloCategoria) { this.modeloCategoria = modeloCategoria; }

    public Long getMarcaId() { return marcaId; }
    public void setMarcaId(Long marcaId) { this.marcaId = marcaId; }

    public String getMarcaNome() { return marcaNome; }
    public void setMarcaNome(String marcaNome) { this.marcaNome = marcaNome; }

    public String getMarcaPaisOrigem() { return marcaPaisOrigem; }
    public void setMarcaPaisOrigem(String marcaPaisOrigem) { this.marcaPaisOrigem = marcaPaisOrigem; }
}
