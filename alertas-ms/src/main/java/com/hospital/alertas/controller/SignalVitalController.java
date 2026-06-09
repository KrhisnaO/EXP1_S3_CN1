package com.hospital.alertas.controller;

import com.hospital.alertas.dto.SignalVitalRequest;
import com.hospital.alertas.model.SignalVital;
import com.hospital.alertas.service.SignalVitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/signals")
@RequiredArgsConstructor
public class SignalVitalController {

    private final SignalVitalService signalService;

    @GetMapping
    public ResponseEntity<List<SignalVital>> getAll() {
        return ResponseEntity.ok(signalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SignalVital> getById(@PathVariable Long id) {
        return ResponseEntity.ok(signalService.findById(id));
    }

    @GetMapping("/criticas")
    public ResponseEntity<List<SignalVital>> getCriticas() {
        return ResponseEntity.ok(signalService.findCriticas());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<SignalVital>> getByPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(signalService.findByPaciente(pacienteId));
    }

    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<SignalVital> create(@PathVariable Long pacienteId,
                                              @Valid @RequestBody SignalVitalRequest request) {
        SignalVital creada = signalService.save(pacienteId, request);
        return ResponseEntity.created(URI.create("/api/signals/" + creada.getId())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SignalVital> update(@PathVariable Long id,
                                              @Valid @RequestBody SignalVitalRequest request) {
        return ResponseEntity.ok(signalService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        signalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
