import { Component, ChangeDetectorRef } from '@angular/core';
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
import { MatRadioModule } from '@angular/material/radio';
import { AiService } from '../../services/ai';

interface Documento {
  id: number;
  titulo: string;
  contenido: string;
  materia: string;
}

interface PreguntaQuiz {
  pregunta: string;
  opciones: string[];
  respuestaCorrecta: string;
  respuestaSeleccionada?: string;
}

interface ProgresoMateria {
  materia: string;
  totalAciertos: number;
  totalPreguntas: number;
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
    MatProgressSpinnerModule,
    MatRadioModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent {
  nombreUsuario: string = 'Estudiante';
  progreso$: Observable<ProgresoMateria[]>;
  // --- Documentos ---
  documentos$: Observable<Documento[]>;
  columnasVisibles: string[] = ['titulo', 'materia'];

  // --- Generador de Resumen ---
  contenidoDocumento: string = '';
  resumenGenerado: string = '';
  cargando: boolean = false;
  errorMensaje: string = '';

  // --- Quiz ---
  cargandoQuiz: boolean = false;
  errorQuiz: string = '';
  preguntas: PreguntaQuiz[] = [];
  quizEnviado: boolean = false;
  resultadoQuiz: string = '';
  materiaQuiz: string = '';

  constructor(
  private router: Router,
  private http: HttpClient,
  private aiService: AiService,
  private cdr: ChangeDetectorRef
) {
  this.documentos$ = this.http.get<Documento[]>('http://localhost:8080/api/v1/documentos');
  this.progreso$ = this.http.get<ProgresoMateria[]>('http://localhost:8080/api/v1/resultados/progreso');
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
    this.cdr.detectChanges(); 
  },
  error: (err) => {
    console.error('Error al generar el resumen:', err);
    this.errorMensaje = 'No se pudo generar el resumen. Intenta de nuevo en unos segundos.';
    this.cargando = false;
    this.cdr.detectChanges()
  }
  });
}

  generarQuiz(): void {
    if (!this.contenidoDocumento.trim()) {
      return;
    }

    this.cargandoQuiz = true;
    this.errorQuiz = '';
    this.preguntas = [];
    this.quizEnviado = false;
    this.resultadoQuiz = '';

    this.aiService.generarPreguntas(this.contenidoDocumento).subscribe({
      next: (res) => {
        try {
          let textoLimpio = res.preguntas.trim();

          textoLimpio = textoLimpio.replace(/```json/g, '').replace(/```/g, '').trim();

          const inicio = textoLimpio.indexOf('[');
          const fin = textoLimpio.lastIndexOf(']');

          if (inicio === -1 || fin === -1) {
            throw new Error('No se encontró un arreglo JSON en la respuesta');
          }

          textoLimpio = textoLimpio.substring(inicio, fin + 1);

          this.preguntas = JSON.parse(textoLimpio);
        } catch (e) {
          console.error('Error al parsear las preguntas:', e);
          console.log('Texto crudo recibido:', res.preguntas);
          this.errorQuiz = 'No pudimos generar el quiz esta vez, inténtalo de nuevo.';
        }
        this.cargandoQuiz = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al generar el quiz:', err);
        this.errorQuiz = 'No se pudo generar el quiz. Intenta de nuevo en unos segundos.';
        this.cargandoQuiz = false;
        this.cdr.detectChanges();
      }
    });
  }

  enviarQuiz(): void {
  let aciertos = 0;

  for (const p of this.preguntas) {
    if (p.respuestaSeleccionada === p.respuestaCorrecta) {
      aciertos++;
    }
  }

  this.resultadoQuiz = `Obtuviste ${aciertos} de ${this.preguntas.length} correctas.`;
  this.quizEnviado = true;
  this.cdr.detectChanges();

  const resultado = {
    materia: this.materiaQuiz || 'General',
    aciertos: aciertos,
    total: this.preguntas.length
  };

  this.http.post('http://localhost:8080/api/v1/resultados', resultado).subscribe({
  next: () => {
    console.log('Resultado guardado correctamente');
    this.progreso$ = this.http.get<ProgresoMateria[]>('http://localhost:8080/api/v1/resultados/progreso');
  },
  error: (err) => {
    console.error('Error al guardar el resultado:', err);
  }
});
}
}