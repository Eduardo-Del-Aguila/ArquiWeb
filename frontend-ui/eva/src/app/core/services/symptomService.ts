import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthService } from './AuthService';
import { environment } from '../../env/environment';
import { Symptom, SymptomInsertDTO } from '../interfaces/symptom.interface';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root',
})
export class SymptomService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = base_url;

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    console.log('Token en SymptomService:', token);
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  listarSintomas() {
    return this.http.get<Symptom[]>(`${this.url}/sintomas/listar`, { headers: this.getHeaders() });
  }

  insertarSintoma(dto: SymptomInsertDTO) {
    return this.http.post<Symptom>(`${this.url}/sintomas/insertar`, dto, { headers: this.getHeaders() });
  }

  eliminarSintoma(id: number) {
  return this.http.delete(`${this.url}/sintomas/eliminar/${id}`, { 
    headers: this.getHeaders(),
    responseType: 'text'
  });
}
}