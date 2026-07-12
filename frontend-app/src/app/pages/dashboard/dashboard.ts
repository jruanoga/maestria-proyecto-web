import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent {
  nombreUsuario: string = 'Estudiante'; // luego vendrá del backend/login

  constructor(private router: Router) {}

  cerrarSesion(): void {
    // Aquí luego limpiarás el token/sesión guardada
    console.log('Cerrando sesión...');
    this.router.navigate(['/login']);
  }
}