package com.hospital.alertas.service;

import com.hospital.alertas.dto.PacienteRequest;
import com.hospital.alertas.exception.RecursoNoEncontradoException;
import com.hospital.alertas.model.Paciente;
import com.hospital.alertas.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public List<Paciente> findAll() {
        return pacienteRepository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Paciente findById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Paciente> findActivos() {
        return pacienteRepository.findByEstadoOrderByNombre("ACTIVO");
    }

    public Paciente save(PacienteRequest request) {
        Paciente paciente = new Paciente();
        cargarDatos(paciente, request);
        return pacienteRepository.save(paciente);
    }

    public Paciente update(Long id, PacienteRequest request) {
        Paciente paciente = findById(id);
        cargarDatos(paciente, request);
        return pacienteRepository.save(paciente);
    }

    public void delete(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    private void cargarDatos(Paciente p, PacienteRequest r) {
        p.setNombre(r.getNombre().trim());
        p.setApellido(r.getApellido().trim());
        p.setHabitacion(r.getHabitacion() != null ? r.getHabitacion().trim() : null);
        p.setDiagnostico(r.getDiagnostico());
        p.setRut(r.getRut() != null ? r.getRut().trim() : null);
        if (r.getFechaNacimiento() != null && !r.getFechaNacimiento().isBlank()) {
            p.setFechaNacimiento(LocalDate.parse(r.getFechaNacimiento()));
        }
        p.setEstado(r.getEstado() != null ? r.getEstado().toUpperCase() : "ACTIVO");
    }
}
