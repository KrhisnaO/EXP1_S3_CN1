package com.hospital.alertas.repository;

import com.hospital.alertas.model.SignalVital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignalVitalRepository extends JpaRepository<SignalVital, Long> {
    List<SignalVital> findByPacienteIdOrderByFechaRegistroDesc(Long pacienteId);
    List<SignalVital> findByCriticoTrueOrderByFechaRegistroDesc();
    List<SignalVital> findAllByOrderByFechaRegistroDesc();
}
