package com.hospital.alertas.repository;

import com.hospital.alertas.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByEstadoOrderByFechaAlertaDesc(String estado);
    List<Alerta> findByPacienteIdOrderByFechaAlertaDesc(Long pacienteId);
    List<Alerta> findBySeveridadOrderByFechaAlertaDesc(String severidad);
    List<Alerta> findAllByOrderByFechaAlertaDesc();
}
