package com.hospital.alertas.dto;

import jakarta.validation.constraints.*;

public class SignalVitalRequest {

    @NotNull(message = "La frecuencia cardíaca es obligatoria")
    @Min(value = 20, message = "Mínimo 20")
    @Max(value = 250, message = "Máximo 250")
    private Integer frecuenciaCardiaca;

    @NotNull(message = "La presión sistólica es obligatoria")
    @Min(value = 40) @Max(value = 260)
    private Integer presionSistolica;

    @NotNull(message = "La presión diastólica es obligatoria")
    @Min(value = 30) @Max(value = 180)
    private Integer presionDiastolica;

    @NotNull(message = "La temperatura es obligatoria")
    @DecimalMin("30.0") @DecimalMax("45.0")
    private Double temperatura;

    @NotNull(message = "La saturación de oxígeno es obligatoria")
    @Min(value = 0) @Max(value = 100)
    private Integer saturacionOxigeno;

    @NotNull(message = "La frecuencia respiratoria es obligatoria")
    @Min(value = 5) @Max(value = 80)
    private Integer frecuenciaRespiratoria;

    public Integer getFrecuenciaCardiaca() { return frecuenciaCardiaca; }
    public void setFrecuenciaCardiaca(Integer v) { this.frecuenciaCardiaca = v; }
    public Integer getPresionSistolica() { return presionSistolica; }
    public void setPresionSistolica(Integer v) { this.presionSistolica = v; }
    public Integer getPresionDiastolica() { return presionDiastolica; }
    public void setPresionDiastolica(Integer v) { this.presionDiastolica = v; }
    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double v) { this.temperatura = v; }
    public Integer getSaturacionOxigeno() { return saturacionOxigeno; }
    public void setSaturacionOxigeno(Integer v) { this.saturacionOxigeno = v; }
    public Integer getFrecuenciaRespiratoria() { return frecuenciaRespiratoria; }
    public void setFrecuenciaRespiratoria(Integer v) { this.frecuenciaRespiratoria = v; }
}
