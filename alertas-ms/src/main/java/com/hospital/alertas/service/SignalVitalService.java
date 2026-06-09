package com.hospital.alertas.service;

import com.hospital.alertas.dto.SignalVitalRequest;
import com.hospital.alertas.exception.RecursoNoEncontradoException;
import com.hospital.alertas.model.Alerta;
import com.hospital.alertas.model.Paciente;
import com.hospital.alertas.model.SignalVital;
import com.hospital.alertas.repository.AlertaRepository;
import com.hospital.alertas.repository.PacienteRepository;
import com.hospital.alertas.repository.SignalVitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SignalVitalService {

    private final SignalVitalRepository signalRepository;
    private final PacienteRepository pacienteRepository;
    private final AlertaRepository alertaRepository;

    @Transactional(readOnly = true)
    public List<SignalVital> findAll() {
        return signalRepository.findAllByOrderByFechaRegistroDesc();
    }

    @Transactional(readOnly = true)
    public List<SignalVital> findByPaciente(Long pacienteId) {
        return signalRepository.findByPacienteIdOrderByFechaRegistroDesc(pacienteId);
    }

    @Transactional(readOnly = true)
    public List<SignalVital> findCriticas() {
        return signalRepository.findByCriticoTrueOrderByFechaRegistroDesc();
    }

    @Transactional(readOnly = true)
    public SignalVital findById(Long id) {
        return signalRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Señal vital no encontrada con id: " + id));
    }

    public SignalVital save(Long pacienteId, SignalVitalRequest request) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + pacienteId));

        SignalVital signal = new SignalVital();
        signal.setPaciente(paciente);
        cargarDatos(signal, request);

        boolean critico = esCritico(signal);
        signal.setCritico(critico);

        SignalVital guardado = signalRepository.save(signal);

        if (critico) {
            generarAlertaAutomatica(paciente, guardado);
        }

        return guardado;
    }

    public SignalVital update(Long id, SignalVitalRequest request) {
        SignalVital signal = findById(id);
        cargarDatos(signal, request);
        signal.setCritico(esCritico(signal));
        return signalRepository.save(signal);
    }

    public void delete(Long id) {
        if (!signalRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Señal vital no encontrada con id: " + id);
        }
        signalRepository.deleteById(id);
    }

    private boolean esCritico(SignalVital s) {
        if (s.getFrecuenciaCardiaca() != null
                && (s.getFrecuenciaCardiaca() < 40 || s.getFrecuenciaCardiaca() > 150)) return true;
        if (s.getPresionSistolica() != null
                && (s.getPresionSistolica() < 80 || s.getPresionSistolica() > 180)) return true;
        if (s.getTemperatura() != null
                && (s.getTemperatura() < 35.0 || s.getTemperatura() > 39.5)) return true;
        if (s.getSaturacionOxigeno() != null && s.getSaturacionOxigeno() < 90) return true;
        if (s.getFrecuenciaRespiratoria() != null
                && (s.getFrecuenciaRespiratoria() < 8 || s.getFrecuenciaRespiratoria() > 30)) return true;
        return false;
    }

    private String calcularSeveridad(SignalVital s) {
        if (s.getFrecuenciaCardiaca() != null
                && (s.getFrecuenciaCardiaca() < 30 || s.getFrecuenciaCardiaca() > 180)) return "CRITICA";
        if (s.getSaturacionOxigeno() != null && s.getSaturacionOxigeno() < 85) return "CRITICA";
        if (s.getTemperatura() != null
                && (s.getTemperatura() < 33.0 || s.getTemperatura() > 41.0)) return "CRITICA";
        return "ALTA";
    }

    private void generarAlertaAutomatica(Paciente paciente, SignalVital signal) {
        Alerta alerta = new Alerta();
        alerta.setPaciente(paciente);
        alerta.setSignal(signal);
        alerta.setTipo("SIGNOS_VITALES_CRITICOS");
        alerta.setSeveridad(calcularSeveridad(signal));
        alerta.setEstado("ACTIVA");
        alerta.setDescripcion("Signos vitales críticos detectados automáticamente para "
                + paciente.getNombre() + " " + paciente.getApellido()
                + " en habitación " + paciente.getHabitacion());
        alertaRepository.save(alerta);
    }

    private void cargarDatos(SignalVital s, SignalVitalRequest r) {
        s.setFrecuenciaCardiaca(r.getFrecuenciaCardiaca());
        s.setPresionSistolica(r.getPresionSistolica());
        s.setPresionDiastolica(r.getPresionDiastolica());
        s.setTemperatura(r.getTemperatura());
        s.setSaturacionOxigeno(r.getSaturacionOxigeno());
        s.setFrecuenciaRespiratoria(r.getFrecuenciaRespiratoria());
    }
}
