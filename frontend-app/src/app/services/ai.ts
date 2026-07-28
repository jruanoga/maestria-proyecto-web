import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AiService {
  private apiUrl = 'http://localhost:8080/api/v1/ia/consulta';

  constructor(private http: HttpClient) {}

  consultarInteligenciaArtificial(pregunta: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?pregunta=${encodeURIComponent(pregunta)}`);
  }
}