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

  // --- Documentos ---
  documentos$: Observable<Documento[]>;
  columnasVisibles: string[] = ['titulo', 'materia'];

  // --- Generador de Resumen con IA ---
  contenidoDocumento: string = '';
  resumenGenerado: string = '';
  cargando: boolean = false;
  errorMensaje: string = '';

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

  generarResumen(): void {
    if (!this.contenidoDocumento.trim()) {
      return;
    }

    this.cargando = true;
    this.resumenGenerado = '';
    this.errorMensaje = '';

    this.aiService.generarResumen(this.contenidoDocumento).subscribe({
      next: (res) => {
        this.resumenGenerado = res.resumen;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al generar el resumen:', err);
        this.errorMensaje = 'No se pudo generar el resumen. Intenta de nuevo en unos segundos.';
        this.cargando = false;
      }
    });
  }
}