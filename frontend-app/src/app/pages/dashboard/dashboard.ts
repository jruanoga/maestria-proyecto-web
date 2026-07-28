import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AiService } from '../../services/ai';

interface Documento {
  id: number;
  titulo: string;
  contenido: string;
  materia: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTableModule,
    MatInputModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent {
  nombreUsuario: string = 'Estudiante';

  // --- Documentos (ya existente) ---
  documentos$: Observable<Documento[]>;
  columnasVisibles: string[] = ['titulo', 'materia'];

  // --- Práctica genérica de IA (Sesión 6) ---
  textoUsuario: string = '';
  respuestaIa: string = '';
  cargando: boolean = false;

  constructor(
    private router: Router,
    private http: HttpClient,
    private aiService: AiService
  ) {
    this.documentos$ = this.http.get<Documento[]>('http://localhost:8080/api/v1/documentos');
  }

  cerrarSesion(): void {
    this.router.navigate(['/login']);
  }

  enviarPregunta(): void {
    if (!this.textoUsuario.trim()) {
      return;
    }

    this.cargando = true;
    this.respuestaIa = '';

    this.aiService.consultarInteligenciaArtificial(this.textoUsuario).subscribe({
      next: (res) => {
        this.respuestaIa = res.respuesta;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al consultar la IA:', err);
        this.respuestaIa = 'Ocurrió un error al procesar tu solicitud con el cerebro.';
        this.cargando = false;
      }
    });
  }
}