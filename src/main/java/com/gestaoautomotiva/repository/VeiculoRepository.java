package com.gestaoautomotiva.repository;

import com.gestaoautomotiva.entity.Veiculo;
import com.gestaoautomotiva.enums.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>,
        JpaSpecificationExecutor<Veiculo> {

    Optional<Veiculo> findByPlacaIgnoreCase(String placa);

    Optional<Veiculo> findByChassiIgnoreCase(String chassi);

    boolean existsByPlacaIgnoreCase(String placa);

    boolean existsByChassiIgnoreCase(String chassi);

    boolean existsByPlacaIgnoreCaseAndIdNot(String placa, Long id);

    boolean existsByChassiIgnoreCaseAndIdNot(String chassi, Long id);

    long countByStatus(StatusVeiculo status);
}
