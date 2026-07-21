import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';

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
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTableModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent {
  nombreUsuario: string = 'Estudiante';
  documentos$: Observable<Documento[]>;
  columnasVisibles: string[] = ['titulo', 'materia'];

  constructor(private router: Router, private http: HttpClient) {
    this.documentos$ = this.http.get<Documento[]>('http://localhost:8080/api/v1/documentos');
  }

  cerrarSesion(): void {
    this.router.navigate(['/login']);
  }
}