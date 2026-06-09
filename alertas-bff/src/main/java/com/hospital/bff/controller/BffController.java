package com.hospital.bff.controller;

import com.hospital.bff.service.MicroservicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BffController {

    private final MicroservicioService ms;

    // ---- HEALTH ----
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "alertas-bff"));
    }

    // ---- PERFIL DEL USUARIO AUTENTICADO CON AZURE AD B2C ----
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal Jwt jwt) {
        // El claim "emails" en Azure AD B2C es un array — se toma el primer elemento
        List<String> emails = jwt.getClaimAsStringList("emails");
        String email = (emails != null && !emails.isEmpty()) ? emails.get(0) : jwt.getSubject();

        return ResponseEntity.ok(Map.of(
                "nombre", jwt.getClaimAsString("name") != null
                        ? jwt.getClaimAsString("name")
                        : jwt.getClaimAsString("given_name") + " " + jwt.getClaimAsString("family_name"),
                "email", email,
                "sub", jwt.getSubject(),
                "issuer", jwt.getIssuer().toString()
        ));
    }

    // ==============================
    // PACIENTES
    // ==============================
    @GetMapping("/pacientes")
    public ResponseEntity<List<Map<String, Object>>> getPacientes() {
        return ms.getPacientes();
    }

    @GetMapping("/pacientes/activos")
    public ResponseEntity<List<Map<String, Object>>> getPacientesActivos() {
        return ms.getPacientesActivos();
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Map<String, Object>> getPaciente(@PathVariable Long id) {
        return ms.getPaciente(id);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Map<String, Object>> createPaciente(@RequestBody Map<String, Object> body) {
        return ms.createPaciente(body);
    }

    @PutMapping("/pacientes/{id}")
    public ResponseEntity<Map<String, Object>> updatePaciente(@PathVariable Long id,
                                                               @RequestBody Map<String, Object> body) {
        return ms.updatePaciente(id, body);
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        return ms.deletePaciente(id);
    }

    // ==============================
    // SEÑALES VITALES
    // ==============================
    @GetMapping("/signals")
    public ResponseEntity<List<Map<String, Object>>> getSignals() {
        return ms.getSignals();
    }

    @GetMapping("/signals/criticas")
    public ResponseEntity<List<Map<String, Object>>> getSignalsCriticas() {
        return ms.getSignalsCriticas();
    }

    @GetMapping("/signals/paciente/{pacienteId}")
    public ResponseEntity<List<Map<String, Object>>> getSignalsPorPaciente(@PathVariable Long pacienteId) {
        return ms.getSignalsPorPaciente(pacienteId);
    }

    @PostMapping("/signals/paciente/{pacienteId}")
    public ResponseEntity<Map<String, Object>> createSignal(@PathVariable Long pacienteId,
                                                             @RequestBody Map<String, Object> body) {
        return ms.createSignal(pacienteId, body);
    }

    @PutMapping("/signals/{id}")
    public ResponseEntity<Map<String, Object>> updateSignal(@PathVariable Long id,
                                                             @RequestBody Map<String, Object> body) {
        return ms.updateSignal(id, body);
    }

    @DeleteMapping("/signals/{id}")
    public ResponseEntity<Void> deleteSignal(@PathVariable Long id) {
        return ms.deleteSignal(id);
    }

    // ==============================
    // ALERTAS
    // ==============================
    @GetMapping("/alertas")
    public ResponseEntity<List<Map<String, Object>>> getAlertas() {
        return ms.getAlertas();
    }

    @GetMapping("/alertas/activas")
    public ResponseEntity<List<Map<String, Object>>> getAlertasActivas() {
        return ms.getAlertasActivas();
    }

    @GetMapping("/alertas/paciente/{pacienteId}")
    public ResponseEntity<List<Map<String, Object>>> getAlertasPorPaciente(@PathVariable Long pacienteId) {
        return ms.getAlertasPorPaciente(pacienteId);
    }

    @PostMapping("/alertas/paciente/{pacienteId}")
    public ResponseEntity<Map<String, Object>> createAlerta(@PathVariable Long pacienteId,
                                                             @RequestBody Map<String, Object> body) {
        return ms.createAlerta(pacienteId, body);
    }

    @PutMapping("/alertas/{id}/atender")
    public ResponseEntity<Map<String, Object>> atenderAlerta(@PathVariable Long id) {
        return ms.atenderAlerta(id);
    }

    @PutMapping("/alertas/{id}")
    public ResponseEntity<Map<String, Object>> updateAlerta(@PathVariable Long id,
                                                             @RequestBody Map<String, Object> body) {
        return ms.updateAlerta(id, body);
    }

    @DeleteMapping("/alertas/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Long id) {
        return ms.deleteAlerta(id);
    }
}
