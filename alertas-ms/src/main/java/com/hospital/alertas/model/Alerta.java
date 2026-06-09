package com.hospital.alertas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ALERTAS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta_seq")
    @SequenceGenerator(name = "alerta_seq", sequenceName = "SEQ_ALERTAS", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "signalesVitales", "alertas"})
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIGNAL_ID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "paciente"})
    private SignalVital signal;

    @NotBlank(message = "El tipo de alerta es obligatorio")
    @Column(name = "TIPO", length = 50, nullable = false)
    private String tipo;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "SEVERIDAD", length = 20)
    private String severidad;

    @Column(name = "ESTADO", length = 20)
    private String estado = "ACTIVA";

    @Column(name = "FECHA_ALERTA", nullable = false, updatable = false)
    private LocalDateTime fechaAlerta;

    @Column(name = "FECHA_ATENCION")
    private LocalDateTime fechaAtencion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.fechaAlerta = now;
        this.fechaActualizacion = now;
        if (this.estado == null || this.estado.isBlank()) this.estado = "ACTIVA";
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
