package com.gestaoautomotiva.service;

import com.gestaoautomotiva.dto.ModeloRequestDTO;
import com.gestaoautomotiva.dto.ModeloResponseDTO;
import com.gestaoautomotiva.entity.Marca;
import com.gestaoautomotiva.entity.Modelo;
import com.gestaoautomotiva.exception.ConflictException;
import com.gestaoautomotiva.exception.ResourceNotFoundException;
import com.gestaoautomotiva.repository.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaService marcaService;

    public ModeloService(ModeloRepository modeloRepository, MarcaService marcaService) {
        this.modeloRepository = modeloRepository;
        this.marcaService = marcaService;
    }

    @Transactional(readOnly = true)
    public List<ModeloResponseDTO> listarTodos() {
        return modeloRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ModeloResponseDTO buscarPorId(Long id) {
        return toResponseDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public List<ModeloResponseDTO> listarPorMarca(Long marcaId) {
        marcaService.findById(marcaId); // valida existência
        return modeloRepository.findByMarcaId(marcaId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ModeloResponseDTO criar(ModeloRequestDTO dto) {
        Marca marca = marcaService.findById(dto.getMarcaId());
        if (modeloRepository.existsByNomeIgnoreCaseAndMarcaId(dto.getNome(), dto.getMarcaId())) {
            throw new ConflictException("Modelo '" + dto.getNome() + "' já existe para esta marca.");
        }
        Modelo modelo = new Modelo();
        modelo.setNome(dto.getNome());
        modelo.setCategoria(dto.getCategoria());
        modelo.setMarca(marca);
        return toResponseDTO(modeloRepository.save(modelo));
    }

    @Transactional
    public ModeloResponseDTO atualizar(Long id, ModeloRequestDTO dto) {
        Modelo modelo = findById(id);
        Marca marca = marcaService.findById(dto.getMarcaId());

        boolean mudou = !modelo.getNome().equalsIgnoreCase(dto.getNome())
                || !modelo.getMarca().getId().equals(dto.getMarcaId());

        if (mudou && modeloRepository.existsByNomeIgnoreCaseAndMarcaId(dto.getNome(), dto.getMarcaId())) {
            throw new ConflictException("Modelo '" + dto.getNome() + "' já existe para esta marca.");
        }
        modelo.setNome(dto.getNome());
        modelo.setCategoria(dto.getCategoria());
        modelo.setMarca(marca);
        return toResponseDTO(modeloRepository.save(modelo));
    }

    @Transactional
    public void deletar(Long id) {
        modeloRepository.delete(findById(id));
    }

    public Modelo findById(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo não encontrado com id: " + id));
    }

    private ModeloResponseDTO toResponseDTO(Modelo modelo) {
        return new ModeloResponseDTO(
                modelo.getId(),
                modelo.getNome(),
                modelo.getCategoria(),
                modelo.getMarca().getId(),
                modelo.getMarca().getNome()
        );
    }
}
