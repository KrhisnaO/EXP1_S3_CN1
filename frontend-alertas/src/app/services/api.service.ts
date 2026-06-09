import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Alerta, Paciente, SignalVital, UsuarioPerfil } from '../models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private api = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // ---- HEALTH / USUARIO ----
  health(): Observable<{ status: string; service: string }> {
    return this.http.get<{ status: string; service: string }>(`${this.api}/health`);
  }

  me(): Observable<UsuarioPerfil> {
    return this.http.get<UsuarioPerfil>(`${this.api}/user`);
  }

  // ---- PACIENTES ----
  getPacientes(): Observable<Paciente[]> {
    return this.http.get<Paciente[]>(`${this.api}/pacientes`);
  }

  getPacientesActivos(): Observable<Paciente[]> {
    return this.http.get<Paciente[]>(`${this.api}/pacientes/activos`);
  }

  getPaciente(id: number): Observable<Paciente> {
    return this.http.get<Paciente>(`${this.api}/pacientes/${id}`);
  }

  crearPaciente(paciente: Paciente): Observable<Paciente> {
    return this.http.post<Paciente>(`${this.api}/pacientes`, paciente);
  }

  actualizarPaciente(id: number, paciente: Paciente): Observable<Paciente> {
    return this.http.put<Paciente>(`${this.api}/pacientes/${id}`, paciente);
  }

  eliminarPaciente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/pacientes/${id}`);
  }

  // ---- SEÑALES VITALES ----
  getSignals(): Observable<SignalVital[]> {
    return this.http.get<SignalVital[]>(`${this.api}/signals`);
  }

  getSignalsCriticas(): Observable<SignalVital[]> {
    return this.http.get<SignalVital[]>(`${this.api}/signals/criticas`);
  }

  getSignalsPorPaciente(pacienteId: number): Observable<SignalVital[]> {
    return this.http.get<SignalVital[]>(`${this.api}/signals/paciente/${pacienteId}`);
  }

  crearSignal(pacienteId: number, signal: SignalVital): Observable<SignalVital> {
    return this.http.post<SignalVital>(`${this.api}/signals/paciente/${pacienteId}`, signal);
  }

  eliminarSignal(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/signals/${id}`);
  }

  // ---- ALERTAS ----
  getAlertas(): Observable<Alerta[]> {
    return this.http.get<Alerta[]>(`${this.api}/alertas`);
  }

  getAlertasActivas(): Observable<Alerta[]> {
    return this.http.get<Alerta[]>(`${this.api}/alertas/activas`);
  }

  getAlertasPorPaciente(pacienteId: number): Observable<Alerta[]> {
    return this.http.get<Alerta[]>(`${this.api}/alertas/paciente/${pacienteId}`);
  }

  atenderAlerta(id: number): Observable<Alerta> {
    return this.http.put<Alerta>(`${this.api}/alertas/${id}/atender`, {});
  }

  eliminarAlerta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/alertas/${id}`);
  }
}
