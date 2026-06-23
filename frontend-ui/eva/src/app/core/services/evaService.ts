import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { EvaPetInsert, EvaPetShow } from '../interfaces/pet.interface';
import { AuthService } from './AuthService';
import { environment } from '../../env/environment';

const base_url = environment.base_url

@Injectable({
  providedIn: 'root',
})
export class EvaPetService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = base_url;

private getHeaders(): HttpHeaders {
  const token = this.authService.getToken();
  console.log('token:', token);
  return new HttpHeaders({
    Authorization: `Bearer ${token}`
  });
}
// http://localhost:8081/api/pet/listar
  listar() {
    return this.http.get<EvaPetShow[]>(`${this.url}/pet/listar`, { headers: this.getHeaders() });
  }

  insertar(dto: EvaPetInsert) {
    return this.http.post<EvaPetShow>(`${this.url}/pet/insertar`, dto, { headers: this.getHeaders() });
  }
}
