package com.hospital.alertas.service;

import com.hospital.alertas.dto.AlertaRequest;
import com.hospital.alertas.exception.RecursoNoEncontradoException;
import com.hospital.alertas.model.Alerta;
import com.hospital.alertas.model.Paciente;
import com.hospital.alertas.repository.AlertaRepository;
import com.hospital.alertas.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public List<Alerta> findAll() {
        return alertaRepository.findAllByOrderByFechaAlertaDesc();
    }

    @Transactional(readOnly = true)
    public List<Alerta> findActivas() {
        return alertaRepository.findByEstadoOrderByFechaAlertaDesc("ACTIVA");
    }

    @Transactional(readOnly = true)
    public List<Alerta> findByPaciente(Long pacienteId) {
        return alertaRepository.findByPacienteIdOrderByFechaAlertaDesc(pacienteId);
    }

    @Transactional(readOnly = true)
    public Alerta findById(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alerta no encontrada con id: " + id));
    }

    public Alerta save(Long pacienteId, AlertaRequest request) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + pacienteId));

        Alerta alerta = new Alerta();
        alerta.setPaciente(paciente);
        cargarDatos(alerta, request);
        return alertaRepository.save(alerta);
    }

    public Alerta atender(Long id) {
        Alerta alerta = findById(id);
        alerta.setEstado("ATENDIDA");
        alerta.setFechaAtencion(LocalDateTime.now());
        return alertaRepository.save(alerta);
    }

    public Alerta update(Long id, AlertaRequest request) {
        Alerta alerta = findById(id);
        cargarDatos(alerta, request);
        return alertaRepository.save(alerta);
    }

    public void delete(Long id) {
        if (!alertaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Alerta no encontrada con id: " + id);
        }
        alertaRepository.deleteById(id);
    }

    private void cargarDatos(Alerta a, AlertaRequest r) {
        a.setTipo(r.getTipo() != null ? r.getTipo().toUpperCase().trim() : "GENERAL");
        a.setDescripcion(r.getDescripcion());
        a.setSeveridad(r.getSeveridad() != null ? r.getSeveridad().toUpperCase() : "MEDIA");
        a.setEstado(r.getEstado() != null ? r.getEstado().toUpperCase() : "ACTIVA");
    }
}
