import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthService } from './authService';
import { environment } from '../../env/environment';
import { HospitalInsertDTO, HospitalShowDTO } from '../interfaces/hospital.interface.ts';

@Injectable({
  providedIn: 'root',
})
export class HospitalService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = `${environment.base_url}/hospital`;

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
  }

  listar() {
    return this.http.get<HospitalShowDTO[]>(`${this.url}/listar`, { headers: this.getHeaders() });
  }

  listarPorId(id: number) {
    return this.http.get<HospitalShowDTO>(`${this.url}/listar/${id}`, { headers: this.getHeaders() });
  }

  insertar(dto: HospitalInsertDTO) {
    return this.http.post<HospitalShowDTO>(`${this.url}/insertar`, dto, { headers: this.getHeaders() });
  }

  actualizar(id: number, dto: HospitalInsertDTO) {
    return this.http.put<HospitalShowDTO>(`${this.url}/actualizar/${id}`, dto, { headers: this.getHeaders() });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.url}/eliminar/${id}`, { headers: this.getHeaders(), responseType: 'text' });
  }
}
