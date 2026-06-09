import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-failed',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="panel center">
      <h2>Error al iniciar sesión</h2>
      <p>No fue posible autenticarse. Por favor intenta de nuevo.</p>
      <a class="btn-primary" routerLink="/">Volver al inicio</a>
    </div>
  `,
})
export class FailedComponent {}
