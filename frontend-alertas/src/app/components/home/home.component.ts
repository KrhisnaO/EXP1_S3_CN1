import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MsalBroadcastService, MsalService } from '@azure/msal-angular';
import {
  AuthenticationResult,
  EventMessage,
  EventType,
  InteractionStatus,
} from '@azure/msal-browser';
import { Subject, filter, takeUntil } from 'rxjs';
import { ApiService } from '../../services/api.service';
import { Alerta, Paciente, SignalVital } from '../../models';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit, OnDestroy {
  loginDisplay = false;
  cargando = false;
  mensaje = '';
  error = '';

  pacientes: Paciente[] = [];
  alertasActivas: Alerta[] = [];
  signalsPaciente: SignalVital[] = [];
  pacienteSeleccionado?: Paciente;

  pacienteForm: FormGroup;
  signalForm: FormGroup;

  private readonly destroying$ = new Subject<void>();

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: MsalService,
    private readonly msalBroadcastService: MsalBroadcastService,
    private readonly api: ApiService,
  ) {
    this.pacienteForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      apellido: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      rut: ['', [Validators.pattern('^[0-9]{7,8}-[0-9Kk]$')]],
      fechaNacimiento: [''],
      habitacion: ['', [Validators.required]],
      diagnostico: ['', [Validators.required, Validators.minLength(5)]],
      estado: ['ACTIVO', [Validators.required]],
    });

    this.signalForm = this.fb.group({
      frecuenciaCardiaca: [80, [Validators.required, Validators.min(20), Validators.max(250)]],
      presionSistolica: [120, [Validators.required, Validators.min(40), Validators.max(260)]],
      presionDiastolica: [80, [Validators.required, Validators.min(30), Validators.max(180)]],
      temperatura: [36.8, [Validators.required, Validators.min(30), Validators.max(45)]],
      saturacionOxigeno: [98, [Validators.required, Validators.min(0), Validators.max(100)]],
      frecuenciaRespiratoria: [16, [Validators.required, Validators.min(5), Validators.max(80)]],
    });
  }

  ngOnInit(): void {
    this.msalBroadcastService.msalSubject$
      .pipe(
        filter((msg: EventMessage) => msg.eventType === EventType.LOGIN_SUCCESS),
        takeUntil(this.destroying$),
      )
      .subscribe((result: EventMessage) => {
        const payload = result.payload as AuthenticationResult;
        this.authService.instance.setActiveAccount(payload.account);
      });

    this.msalBroadcastService.inProgress$
      .pipe(
        filter((status: InteractionStatus) => status === InteractionStatus.None),
        takeUntil(this.destroying$),
      )
      .subscribe(() => {
        this.loginDisplay = this.authService.instance.getAllAccounts().length > 0;
        if (this.loginDisplay) {
          this.cargarDatos();
        }
      });
  }

  cargarDatos(): void {
    this.error = '';
    this.cargando = true;
    this.api.getPacientes().subscribe({
      next: (data) => {
        this.pacientes = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = this.extractError(err);
        this.cargando = false;
      },
    });
    this.api.getAlertasActivas().subscribe({
      next: (data) => (this.alertasActivas = data),
      error: () => {},
    });
  }

  guardarPaciente(): void {
    this.mensaje = '';
    this.error = '';
    if (this.pacienteForm.invalid) {
      this.pacienteForm.markAllAsTouched();
      this.error = 'Revisa los campos obligatorios.';
      return;
    }
    this.cargando = true;
    this.api.crearPaciente(this.pacienteForm.value as Paciente).subscribe({
      next: () => {
        this.mensaje = 'Paciente registrado correctamente.';
        this.pacienteForm.reset({ estado: 'ACTIVO' });
        this.cargarDatos();
      },
      error: (err) => {
        this.error = this.extractError(err);
        this.cargando = false;
      },
    });
  }

  seleccionarPaciente(paciente: Paciente): void {
    this.pacienteSeleccionado = paciente;
    if (!paciente.id) return;
    this.api.getSignalsPorPaciente(paciente.id).subscribe({
      next: (data) => (this.signalsPaciente = data),
      error: (err) => (this.error = this.extractError(err)),
    });
  }

  registrarSignos(): void {
    this.mensaje = '';
    this.error = '';
    if (!this.pacienteSeleccionado?.id) {
      this.error = 'Selecciona un paciente antes de registrar signos vitales.';
      return;
    }
    if (this.signalForm.invalid) {
      this.signalForm.markAllAsTouched();
      this.error = 'Revisa los valores de los signos vitales.';
      return;
    }
    this.cargando = true;
    this.api
      .crearSignal(this.pacienteSeleccionado.id, this.signalForm.value as SignalVital)
      .subscribe({
        next: (signal) => {
          this.mensaje = signal.critico
            ? 'Signos críticos registrados. Se generó una alerta automática.'
            : 'Signos vitales registrados correctamente.';
          this.seleccionarPaciente(this.pacienteSeleccionado!);
          this.cargarDatos();
        },
        error: (err) => {
          this.error = this.extractError(err);
          this.cargando = false;
        },
      });
  }

  atenderAlerta(alerta: Alerta): void {
    if (!alerta.id) return;
    this.api.atenderAlerta(alerta.id).subscribe({
      next: () => {
        this.mensaje = `Alerta #${alerta.id} marcada como atendida.`;
        this.cargarDatos();
      },
      error: (err) => (this.error = this.extractError(err)),
    });
  }

  eliminarPaciente(paciente: Paciente): void {
    if (!paciente.id) return;
    this.api.eliminarPaciente(paciente.id).subscribe({
      next: () => {
        this.mensaje = `Paciente ${paciente.nombre} ${paciente.apellido} eliminado.`;
        if (this.pacienteSeleccionado?.id === paciente.id) {
          this.pacienteSeleccionado = undefined;
          this.signalsPaciente = [];
        }
        this.cargarDatos();
      },
      error: (err) => (this.error = this.extractError(err)),
    });
  }

  campoInvalido(form: FormGroup, campo: string): boolean {
    const control = form.get(campo);
    return !!control && control.invalid && (control.dirty || control.touched);
  }

  ngOnDestroy(): void {
    this.destroying$.next();
    this.destroying$.complete();
  }

  private extractError(err: unknown): string {
    if (err && typeof err === 'object' && 'error' in err) {
      const e = (err as { error?: { mensaje?: string; detalles?: object; error?: string } }).error;
      if (e?.mensaje) return e.mensaje;
      if (e?.error) return e.error;
      if (e?.detalles) return JSON.stringify(e.detalles);
    }
    return 'No se pudo completar la operación. Verifica la conexión.';
  }
}
