package com.gestaoautomotiva.dto;

import com.gestaoautomotiva.enums.StatusVeiculo;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class VeiculoRequestDTO {

    @NotBlank(message = "Cor é obrigatória")
    private String cor;

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1900, message = "Ano inválido")
    @Max(value = 2100, message = "Ano inválido")
    private Integer ano;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "Quilometragem é obrigatória")
    @Min(value = 0, message = "Quilometragem não pode ser negativa")
    private Integer quilometragem;

    @NotNull(message = "Status é obrigatório")
    private StatusVeiculo status;

    @NotBlank(message = "Placa é obrigatória")
    private String placa;

    @NotBlank(message = "Chassi é obrigatório")
    private String chassi;

    @NotNull(message = "ID do modelo é obrigatório")
    private Long modeloId;

    public VeiculoRequestDTO() {}

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
}
