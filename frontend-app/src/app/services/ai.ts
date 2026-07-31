import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AiService {
  private apiUrl = `${environment.apiUrl}/api/v1/ia`;

  constructor(private http: HttpClient) {}

  consultarInteligenciaArtificial(pregunta: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/consulta?pregunta=${encodeURIComponent(pregunta)}`);
  }

  generarResumen(contenido: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/resumen`, { contenido });
  }

  generarPreguntas(contenido: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/preguntas`, { contenido });
  }
}