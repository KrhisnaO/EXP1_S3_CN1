package com.hospital.alertas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SIGNALS_VITALES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SignalVital {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signal_seq")
    @SequenceGenerator(name = "signal_seq", sequenceName = "SEQ_SIGNALS", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "signalesVitales", "alertas"})
    private Paciente paciente;

    @NotNull(message = "La frecuencia cardíaca es obligatoria")
    @Min(value = 20, message = "La frecuencia cardíaca no puede ser menor a 20")
    @Max(value = 250, message = "La frecuencia cardíaca no puede ser mayor a 250")
    @Column(name = "FRECUENCIA_CARDIACA")
    private Integer frecuenciaCardiaca;

    @NotNull(message = "La presión sistólica es obligatoria")
    @Min(value = 40, message = "La presión sistólica no puede ser menor a 40")
    @Max(value = 260, message = "La presión sistólica no puede ser mayor a 260")
    @Column(name = "PRESION_SISTOLICA")
    private Integer presionSistolica;

    @NotNull(message = "La presión diastólica es obligatoria")
    @Min(value = 30, message = "La presión diastólica no puede ser menor a 30")
    @Max(value = 180, message = "La presión diastólica no puede ser mayor a 180")
    @Column(name = "PRESION_DIASTOLICA")
    private Integer presionDiastolica;

    @NotNull(message = "La temperatura es obligatoria")
    @DecimalMin(value = "30.0", message = "La temperatura no puede ser menor a 30.0")
    @DecimalMax(value = "45.0", message = "La temperatura no puede ser mayor a 45.0")
    @Column(name = "TEMPERATURA")
    private Double temperatura;

    @NotNull(message = "La saturación de oxígeno es obligatoria")
    @Min(value = 0, message = "La saturación no puede ser menor a 0")
    @Max(value = 100, message = "La saturación no puede ser mayor a 100")
    @Column(name = "SATURACION_OXIGENO")
    private Integer saturacionOxigeno;

    @NotNull(message = "La frecuencia respiratoria es obligatoria")
    @Min(value = 5, message = "La frecuencia respiratoria no puede ser menor a 5")
    @Max(value = 80, message = "La frecuencia respiratoria no puede ser mayor a 80")
    @Column(name = "FRECUENCIA_RESPIRATORIA")
    private Integer frecuenciaRespiratoria;

    @Column(name = "FECHA_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "CRITICO")
    private Boolean critico = false;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        if (this.critico == null) this.critico = false;
    }
}
