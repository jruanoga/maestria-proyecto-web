import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, MatCardModule, MatInputModule, MatButtonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  private http = inject(HttpClient);
  private router = inject(Router);

  email = '';
  password = '';

  hacerLogin() {
    const credenciales = {
      email: this.email,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/api/v1/auth/login', credenciales).subscribe({
      next: (respuesta) => {
        localStorage.setItem('auth_token', respuesta.token);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        alert("Error al iniciar sesión: Revisa tus credenciales.");
      }
    });
  }
}