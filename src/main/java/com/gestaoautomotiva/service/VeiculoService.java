package com.gestaoautomotiva.service;

import com.gestaoautomotiva.dto.VeiculoFiltroDTO;
import com.gestaoautomotiva.dto.VeiculoRequestDTO;
import com.gestaoautomotiva.dto.VeiculoResponseDTO;
import com.gestaoautomotiva.dto.VeiculoUpdateDTO;
import com.gestaoautomotiva.entity.Modelo;
import com.gestaoautomotiva.entity.Veiculo;
import com.gestaoautomotiva.exception.ConflictException;
import com.gestaoautomotiva.exception.ResourceNotFoundException;
import com.gestaoautomotiva.repository.VeiculoRepository;
import com.gestaoautomotiva.repository.VeiculoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloService modeloService;

    public VeiculoService(VeiculoRepository veiculoRepository, ModeloService modeloService) {
        this.veiculoRepository = veiculoRepository;
        this.modeloService = modeloService;
    }

    @Transactional(readOnly = true)
    public Page<VeiculoResponseDTO> listarComFiltros(VeiculoFiltroDTO filtro, Pageable pageable) {
        Specification<Veiculo> spec = VeiculoSpecification.comFiltros(filtro);
        return veiculoRepository.findAll(spec, pageable).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public VeiculoResponseDTO buscarPorId(Long id) {
        return toResponseDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public VeiculoResponseDTO buscarPorPlaca(String placa) {
        Veiculo veiculo = veiculoRepository.findByPlacaIgnoreCase(placa)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com placa: " + placa));
        return toResponseDTO(veiculo);
    }

    @Transactional
    public VeiculoResponseDTO criar(VeiculoRequestDTO dto) {
        validarUnicos(dto.getPlaca(), dto.getChassi(), null);
        Modelo modelo = modeloService.findById(dto.getModeloId());
        Veiculo veiculo = new Veiculo();
        preencher(veiculo, dto, modelo);
        return toResponseDTO(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponseDTO atualizar(Long id, VeiculoRequestDTO dto) {
        Veiculo veiculo = findById(id);
        validarUnicos(dto.getPlaca(), dto.getChassi(), id);
        Modelo modelo = modeloService.findById(dto.getModeloId());
        preencher(veiculo, dto, modelo);
        return toResponseDTO(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponseDTO atualizarParcial(Long id, VeiculoUpdateDTO dto) {
        Veiculo veiculo = findById(id);
        if (dto.getPreco() != null)         veiculo.setPreco(dto.getPreco());
        if (dto.getQuilometragem() != null)  veiculo.setQuilometragem(dto.getQuilometragem());
        if (dto.getStatus() != null)         veiculo.setStatus(dto.getStatus());
        return toResponseDTO(veiculoRepository.save(veiculo));
    }

    @Transactional
    public void deletar(Long id) {
        veiculoRepository.delete(findById(id));
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void validarUnicos(String placa, String chassi, Long idExcluir) {
        if (idExcluir == null) {
            if (veiculoRepository.existsByPlacaIgnoreCase(placa))
                throw new ConflictException("Já existe um veículo com a placa: " + placa);
            if (veiculoRepository.existsByChassiIgnoreCase(chassi))
                throw new ConflictException("Já existe um veículo com o chassi: " + chassi);
        } else {
            if (veiculoRepository.existsByPlacaIgnoreCaseAndIdNot(placa, idExcluir))
                throw new ConflictException("Já existe um veículo com a placa: " + placa);
            if (veiculoRepository.existsByChassiIgnoreCaseAndIdNot(chassi, idExcluir))
                throw new ConflictException("Já existe um veículo com o chassi: " + chassi);
        }
    }

    private void preencher(Veiculo v, VeiculoRequestDTO dto, Modelo modelo) {
        v.setCor(dto.getCor());
        v.setAno(dto.getAno());
        v.setPreco(dto.getPreco());
        v.setQuilometragem(dto.getQuilometragem());
        v.setStatus(dto.getStatus());
        v.setPlaca(dto.getPlaca().toUpperCase());
        v.setChassi(dto.getChassi().toUpperCase());
        v.setModelo(modelo);
    }

    public Veiculo findById(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com id: " + id));
    }

    private VeiculoResponseDTO toResponseDTO(Veiculo v) {
        Modelo m = v.getModelo();
        return new VeiculoResponseDTO(
                v.getId(), v.getCor(), v.getAno(), v.getPreco(),
                v.getQuilometragem(), v.getStatus(), v.getPlaca(), v.getChassi(),
                m.getId(), m.getNome(), m.getCategoria(),
                m.getMarca().getId(), m.getMarca().getNome(), m.getMarca().getPaisOrigem()
        );
    }
}
