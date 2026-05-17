package com.gestaoautomotiva.repository;

import com.gestaoautomotiva.dto.VeiculoFiltroDTO;
import com.gestaoautomotiva.entity.Modelo;
import com.gestaoautomotiva.entity.Veiculo;
import com.gestaoautomotiva.enums.StatusVeiculo;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VeiculoSpecification {

    private VeiculoSpecification() {}

    public static Specification<Veiculo> comFiltros(VeiculoFiltroDTO filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Join com modelo e marca para filtros relacionados
            Join<Veiculo, Modelo> modeloJoin = root.join("modelo", JoinType.LEFT);

            if (filtro.getMarcaId() != null) {
                predicates.add(cb.equal(modeloJoin.get("marca").get("id"), filtro.getMarcaId()));
            }

            if (filtro.getModeloId() != null) {
                predicates.add(cb.equal(modeloJoin.get("id"), filtro.getModeloId()));
            }

            if (filtro.getPrecoMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("preco"), filtro.getPrecoMin()));
            }

            if (filtro.getPrecoMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("preco"), filtro.getPrecoMax()));
            }

            if (filtro.getAnoMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("ano"), filtro.getAnoMin()));
            }

            if (filtro.getAnoMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("ano"), filtro.getAnoMax()));
            }

            if (filtro.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filtro.getStatus()));
            }

            // Evitar duplicatas por causa do join
            query.distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
