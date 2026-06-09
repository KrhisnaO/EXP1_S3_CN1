package com.hospital.bff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MicroservicioService {

    private final RestTemplate restTemplate;
    private final String msAlertasUrl;

    // ---- PACIENTES ----
    public ResponseEntity<List<Map<String, Object>>> getPacientes() {
        return restTemplate.exchange(
                msAlertasUrl + "/api/pacientes",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<List<Map<String, Object>>> getPacientesActivos() {
        return restTemplate.exchange(
                msAlertasUrl + "/api/pacientes/activos",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Map<String, Object>> getPaciente(Long id) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/pacientes/" + id,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Map<String, Object>> createPaciente(Map<String, Object> body) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/pacientes",
                HttpMethod.POST, new HttpEntity<>(body, jsonHeaders()),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Map<String, Object>> updatePaciente(Long id, Map<String, Object> body) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/pacientes/" + id,
                HttpMethod.PUT, new HttpEntity<>(body, jsonHeaders()),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Void> deletePaciente(Long id) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/pacientes/" + id,
                HttpMethod.DELETE, null, Void.class);
    }

    // ---- SEÑALES VITALES ----
    public ResponseEntity<List<Map<String, Object>>> getSignals() {
        return restTemplate.exchange(
                msAlertasUrl + "/api/signals",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<List<Map<String, Object>>> getSignalsCriticas() {
        return restTemplate.exchange(
                msAlertasUrl + "/api/signals/criticas",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<List<Map<String, Object>>> getSignalsPorPaciente(Long pacienteId) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/signals/paciente/" + pacienteId,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Map<String, Object>> createSignal(Long pacienteId, Map<String, Object> body) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/signals/paciente/" + pacienteId,
                HttpMethod.POST, new HttpEntity<>(body, jsonHeaders()),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Map<String, Object>> updateSignal(Long id, Map<String, Object> body) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/signals/" + id,
                HttpMethod.PUT, new HttpEntity<>(body, jsonHeaders()),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Void> deleteSignal(Long id) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/signals/" + id,
                HttpMethod.DELETE, null, Void.class);
    }

    // ---- ALERTAS ----
    public ResponseEntity<List<Map<String, Object>>> getAlertas() {
        return restTemplate.exchange(
                msAlertasUrl + "/api/alertas",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<List<Map<String, Object>>> getAlertasActivas() {
        return restTemplate.exchange(
                msAlertasUrl + "/api/alertas/activas",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<List<Map<String, Object>>> getAlertasPorPaciente(Long pacienteId) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/alertas/paciente/" + pacienteId,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Map<String, Object>> createAlerta(Long pacienteId, Map<String, Object> body) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/alertas/paciente/" + pacienteId,
                HttpMethod.POST, new HttpEntity<>(body, jsonHeaders()),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Map<String, Object>> atenderAlerta(Long id) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/alertas/" + id + "/atender",
                HttpMethod.PUT, new HttpEntity<>(null, jsonHeaders()),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Map<String, Object>> updateAlerta(Long id, Map<String, Object> body) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/alertas/" + id,
                HttpMethod.PUT, new HttpEntity<>(body, jsonHeaders()),
                new ParameterizedTypeReference<>() {});
    }

    public ResponseEntity<Void> deleteAlerta(Long id) {
        return restTemplate.exchange(
                msAlertasUrl + "/api/alertas/" + id,
                HttpMethod.DELETE, null, Void.class);
    }

    private HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
