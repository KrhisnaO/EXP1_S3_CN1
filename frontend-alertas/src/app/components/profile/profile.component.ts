import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MsalService } from '@azure/msal-angular';
import { AccountInfo } from '@azure/msal-browser';
import { ApiService } from '../../services/api.service';
import { UsuarioPerfil } from '../../models';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {
  account: AccountInfo | null = null;
  perfil: UsuarioPerfil | null = null;
  error = '';

  constructor(
    private readonly authService: MsalService,
    private readonly api: ApiService,
  ) {}

  ngOnInit(): void {
    this.account =
      this.authService.instance.getActiveAccount() ??
      this.authService.instance.getAllAccounts()[0] ??
      null;

    // Llamar al BFF para obtener los claims del JWT
    this.api.me().subscribe({
      next: (p) => (this.perfil = p),
      error: (err) =>
        (this.error = 'No se pudo obtener el perfil: ' + (err?.message ?? '')),
    });
  }
}
