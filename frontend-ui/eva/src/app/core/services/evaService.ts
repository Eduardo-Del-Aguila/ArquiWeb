import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { EvaPetInsert, EvaPetShow } from '../interfaces/pet.interface';
import { AuthService } from './authService';
import { environment } from '../../env/environment';

const base_url = environment.base_url

@Injectable({
  providedIn: 'root',
})
export class EvaPetService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = 'http://localhost:8080/api/pet';

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
  }

  listar() {
    return this.http.get<EvaPetShow[]>(`${this.url}/listar`, { headers: this.getHeaders() });
  }

  insertar(dto: EvaPetInsert) {
    return this.http.post<EvaPetShow>(`${this.url}/insertar`, dto, { headers: this.getHeaders() });
  }
}
