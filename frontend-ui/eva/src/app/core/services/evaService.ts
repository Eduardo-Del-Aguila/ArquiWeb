import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { EvaPetInsert, EvaPetShow } from '../interfaces/pet.interface';
import { AuthService } from './AuthService';
import { environment } from '../../env/environment';
import { EvaPetReportDTO } from '../interfaces/evaPetReport';
import { UserRolReportDTO } from '../interfaces/users.interface';

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
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }


  listar() {
    return this.http.get<EvaPetShow[]>(`${this.url}/pet/listar`, { headers: this.getHeaders() });
  }

  listByUserId(email: string) {
    return this.http.get<EvaPetShow[]>(`${this.url}/pet/listar-patient/${email}`, { headers: this.getHeaders() });
  }

  insertar(dto: EvaPetInsert) {
    return this.http.post<EvaPetShow>(`${this.url}/pet/insertar`, dto, { headers: this.getHeaders() });
  }
  actualizar(id: number, dto: EvaPetInsert) {
    return this.http.put<EvaPetInsert>(`${this.url}/pet/actualizar/${id}`, dto, { headers: this.getHeaders() });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.url}/pet/eliminar/${id}`, { headers: this.getHeaders(), responseType: 'text' });
  }
  listarPorEmail(email: string) {
    return this.http.get<EvaPetShow[]>(`${this.url}/pet/listar-patient/${email}`, { headers: this.getHeaders() });
  }

  listarPorNivel() {
    return this.http.get<EvaPetShow[]>(`${this.url}/pet/mis-mascotas/por-nivel`, { headers: this.getHeaders() });
  }

  reporteNivel() {
    return this.http.get<EvaPetReportDTO[]>(`${this.url}/pet/reporte/experience`, { headers: this.getHeaders() });
  }



}
