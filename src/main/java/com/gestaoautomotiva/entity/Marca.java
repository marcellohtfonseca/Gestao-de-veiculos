package com.gestaoautomotiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "marca")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da marca é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "País de origem é obrigatório")
    @Column(name = "pais_origem", nullable = false, length = 100)
    private String paisOrigem;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Modelo> modelos = new ArrayList<>();

    public Marca() {}

    public Marca(Long id, String nome, String paisOrigem) {
        this.id = id;
        this.nome = nome;
        this.paisOrigem = paisOrigem;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPaisOrigem() { return paisOrigem; }
    public void setPaisOrigem(String paisOrigem) { this.paisOrigem = paisOrigem; }

    public List<Modelo> getModelos() { return modelos; }
    public void setModelos(List<Modelo> modelos) { this.modelos = modelos; }
}
