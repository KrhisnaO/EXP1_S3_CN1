package com.hospital.alertas.controller;

import com.hospital.alertas.dto.AlertaRequest;
import com.hospital.alertas.model.Alerta;
import com.hospital.alertas.service.AlertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<Alerta>> getAll() {
        return ResponseEntity.ok(alertaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> getById(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.findById(id));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Alerta>> getActivas() {
        return ResponseEntity.ok(alertaService.findActivas());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Alerta>> getByPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(alertaService.findByPaciente(pacienteId));
    }

    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<Alerta> create(@PathVariable Long pacienteId,
                                         @Valid @RequestBody AlertaRequest request) {
        Alerta creada = alertaService.save(pacienteId, request);
        return ResponseEntity.created(URI.create("/api/alertas/" + creada.getId())).body(creada);
    }

    @PutMapping("/{id}/atender")
    public ResponseEntity<Alerta> atender(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.atender(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerta> update(@PathVariable Long id,
                                         @Valid @RequestBody AlertaRequest request) {
        return ResponseEntity.ok(alertaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
