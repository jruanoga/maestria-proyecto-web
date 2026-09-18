import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, FormsModule, MatCardModule, MatInputModule, MatButtonModule],
  templateUrl: './registro.html',
  styleUrl: './registro.css'
})
export class RegistroComponent {
  private http = inject(HttpClient);
  private router = inject(Router);

  nombre = '';
  email = '';
  password = '';
  errorMensaje = '';
  cargando = false;

  hacerRegistro() {
    if (!this.nombre.trim() || !this.email.trim() || !this.password.trim()) {
      return;
    }

    this.cargando = true;
    this.errorMensaje = '';

    const datos = {
      nombre: this.nombre,
      email: this.email,
      password: this.password
    };

    this.http.post<any>(`${environment.apiUrl}/api/v1/auth/registro`, datos).subscribe({
      next: () => {
        this.cargando = false;
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.cargando = false;
        if (err.status === 409) {
          this.errorMensaje = 'Ese correo ya está registrado.';
        } else {
          this.errorMensaje = 'Ocurrió un error al registrar. Intenta de nuevo.';
        }
      }
    });
  }
}