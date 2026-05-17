package com.gestaoautomotiva.service;

import com.gestaoautomotiva.dto.MarcaRequestDTO;
import com.gestaoautomotiva.dto.MarcaResponseDTO;
import com.gestaoautomotiva.entity.Marca;
import com.gestaoautomotiva.exception.ConflictException;
import com.gestaoautomotiva.exception.ResourceNotFoundException;
import com.gestaoautomotiva.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Transactional(readOnly = true)
    public List<MarcaResponseDTO> listarTodas() {
        return marcaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MarcaResponseDTO buscarPorId(Long id) {
        return toResponseDTO(findById(id));
    }

    @Transactional
    public MarcaResponseDTO criar(MarcaRequestDTO dto) {
        if (marcaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new ConflictException("Marca com nome '" + dto.getNome() + "' já existe.");
        }
        Marca marca = new Marca();
        marca.setNome(dto.getNome());
        marca.setPaisOrigem(dto.getPaisOrigem());
        return toResponseDTO(marcaRepository.save(marca));
    }

    @Transactional
    public MarcaResponseDTO atualizar(Long id, MarcaRequestDTO dto) {
        Marca marca = findById(id);
        if (!marca.getNome().equalsIgnoreCase(dto.getNome())
                && marcaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new ConflictException("Marca com nome '" + dto.getNome() + "' já existe.");
        }
        marca.setNome(dto.getNome());
        marca.setPaisOrigem(dto.getPaisOrigem());
        return toResponseDTO(marcaRepository.save(marca));
    }

    @Transactional
    public void deletar(Long id) {
        marcaRepository.delete(findById(id));
    }

    public Marca findById(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada com id: " + id));
    }

    private MarcaResponseDTO toResponseDTO(Marca marca) {
        return new MarcaResponseDTO(marca.getId(), marca.getNome(), marca.getPaisOrigem());
    }
}
