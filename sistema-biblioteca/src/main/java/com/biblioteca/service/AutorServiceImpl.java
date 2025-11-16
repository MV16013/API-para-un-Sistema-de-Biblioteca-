package com.biblioteca.service;

import com.biblioteca.dto.autor.AutorCreateDTO;
import com.biblioteca.dto.autor.AutorResponseDTO;
import com.biblioteca.dto.autor.AutorUpdateDTO;
import com.biblioteca.entity.Autor;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;

    @Override
    @Transactional
    public AutorResponseDTO crearAutor(AutorCreateDTO dto) {
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setFechaNacimiento(dto.getFechaNacimiento());
        autor.setBiografia(dto.getBiografia());

        Autor autorGuardado = autorRepository.save(autor);
        return convertirAResponseDTO(autorGuardado);
    }

    @Override
    public AutorResponseDTO obtenerAutorPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + id));
        return convertirAResponseDTO(autor);
    }

    @Override
    public List<AutorResponseDTO> listarTodosLosAutores() {
        return autorRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AutorResponseDTO actualizarAutor(Long id, AutorUpdateDTO dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            autor.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            autor.setApellido(dto.getApellido());
        }
        if (dto.getNacionalidad() != null) {
            autor.setNacionalidad(dto.getNacionalidad());
        }
        if (dto.getFechaNacimiento() != null) {
            autor.setFechaNacimiento(dto.getFechaNacimiento());
        }
        if (dto.getBiografia() != null) {
            autor.setBiografia(dto.getBiografia());
        }

        Autor autorActualizado = autorRepository.save(autor);
        return convertirAResponseDTO(autorActualizado);
    }

    @Override
    @Transactional
    public void eliminarAutor(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Autor no encontrado con id: " + id);
        }
        autorRepository.deleteById(id);
    }

    @Override
    public List<AutorResponseDTO> buscarAutoresPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AutorResponseDTO> buscarAutoresPorNacionalidad(String nacionalidad) {
        return autorRepository.findByNacionalidad(nacionalidad).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AutorResponseDTO> listarAutoresConLibrosDisponibles() {
        return autorRepository.findAutoresConLibrosDisponibles().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    private AutorResponseDTO convertirAResponseDTO(Autor autor) {
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setApellido(autor.getApellido());
        dto.setNombreCompleto(autor.getNombreCompleto());
        dto.setNacionalidad(autor.getNacionalidad());
        dto.setFechaNacimiento(autor.getFechaNacimiento());
        dto.setBiografia(autor.getBiografia());
        dto.setFechaRegistro(autor.getFechaRegistro());
        dto.setFechaActualizacion(autor.getFechaActualizacion());
        return dto;
    }
}