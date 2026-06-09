// Modelos tipados para toda la aplicación

export interface Paciente {
  id?: number;
  nombre: string;
  apellido: string;
  fechaNacimiento?: string;
  rut?: string;
  habitacion: string;
  diagnostico?: string;
  estado?: string;
  fechaRegistro?: string;
  fechaActualizacion?: string;
}

export interface SignalVital {
  id?: number;
  paciente?: { id: number; nombre: string; apellido: string };
  frecuenciaCardiaca: number;
  presionSistolica: number;
  presionDiastolica: number;
  temperatura: number;
  saturacionOxigeno: number;
  frecuenciaRespiratoria: number;
  critico?: boolean;
  fechaRegistro?: string;
}

export interface Alerta {
  id?: number;
  paciente?: { id: number; nombre: string; apellido: string; habitacion: string };
  signal?: { id: number };
  tipo: string;
  descripcion?: string;
  severidad?: string;
  estado?: string;
  fechaAlerta?: string;
  fechaAtencion?: string;
  fechaActualizacion?: string;
}

export interface UsuarioPerfil {
  nombre: string;
  email: string;
  sub: string;
  issuer?: string;
}
