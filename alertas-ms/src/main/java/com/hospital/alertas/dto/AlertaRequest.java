package com.hospital.alertas.dto;

import jakarta.validation.constraints.NotBlank;

public class AlertaRequest {

    @NotBlank(message = "El tipo de alerta es obligatorio")
    private String tipo;

    private String descripcion;

    private String severidad;

    private String estado;

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getSeveridad() { return severidad; }
    public void setSeveridad(String severidad) { this.severidad = severidad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
