package com.hospital.alertas.controller;

import com.hospital.alertas.dto.PacienteRequest;
import com.hospital.alertas.model.Paciente;
import com.hospital.alertas.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> getAll() {
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Paciente>> getActivos() {
        return ResponseEntity.ok(pacienteService.findActivos());
    }

    @PostMapping
    public ResponseEntity<Paciente> create(@Valid @RequestBody PacienteRequest request) {
        Paciente creado = pacienteService.save(request);
        return ResponseEntity.created(URI.create("/api/pacientes/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> update(@PathVariable Long id,
                                           @Valid @RequestBody PacienteRequest request) {
        return ResponseEntity.ok(pacienteService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
