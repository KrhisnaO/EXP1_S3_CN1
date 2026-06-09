package com.hospital.alertas.repository;

import com.hospital.alertas.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByRut(String rut);
    List<Paciente> findByEstadoOrderByNombre(String estado);
    List<Paciente> findAllByOrderByIdDesc();
}
