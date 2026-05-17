package com.gestaoautomotiva.repository;

import com.gestaoautomotiva.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    List<Modelo> findByMarcaId(Long marcaId);

    List<Modelo> findByCategoriaIgnoreCase(String categoria);

    boolean existsByNomeIgnoreCaseAndMarcaId(String nome, Long marcaId);
}
