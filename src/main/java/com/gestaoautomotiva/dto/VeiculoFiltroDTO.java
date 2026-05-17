package com.gestaoautomotiva.dto;

import com.gestaoautomotiva.enums.StatusVeiculo;

import java.math.BigDecimal;

public class VeiculoFiltroDTO {

    private Long marcaId;
    private Long modeloId;
    private BigDecimal precoMin;
    private BigDecimal precoMax;
    private Integer anoMin;
    private Integer anoMax;
    private StatusVeiculo status;

    public VeiculoFiltroDTO() {}

    public VeiculoFiltroDTO(Long marcaId, Long modeloId, BigDecimal precoMin,
                             BigDecimal precoMax, Integer anoMin, Integer anoMax,
                             StatusVeiculo status) {
        this.marcaId = marcaId;
        this.modeloId = modeloId;
        this.precoMin = precoMin;
        this.precoMax = precoMax;
        this.anoMin = anoMin;
        this.anoMax = anoMax;
        this.status = status;
    }

    public Long getMarcaId() { return marcaId; }
    public void setMarcaId(Long marcaId) { this.marcaId = marcaId; }

    public Long getModeloId() { return modeloId; }
    public void setModeloId(Long modeloId) { this.modeloId = modeloId; }

    public BigDecimal getPrecoMin() { return precoMin; }
    public void setPrecoMin(BigDecimal precoMin) { this.precoMin = precoMin; }

    public BigDecimal getPrecoMax() { return precoMax; }
    public void setPrecoMax(BigDecimal precoMax) { this.precoMax = precoMax; }

    public Integer getAnoMin() { return anoMin; }
    public void setAnoMin(Integer anoMin) { this.anoMin = anoMin; }

    public Integer getAnoMax() { return anoMax; }
    public void setAnoMax(Integer anoMax) { this.anoMax = anoMax; }

    public StatusVeiculo getStatus() { return status; }
    public void setStatus(StatusVeiculo status) { this.status = status; }
}
