import { inject, Injectable } from '@angular/core';
import { environment } from '../../env/environment'; // Ajusta la ruta a tu env
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './AuthService'; // Ajusta la ruta a tu auth.service
import { AlertsInsertDTO, ShowAlertsDTO } from '../interfaces/alert.interface'; // Ajusta la ruta

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  // Asumiendo que base_url es 'http://localhost:8080/api'
  private url = `${environment.base_url}/alerts`;

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  listar() {
    return this.http.get<AlertsInsertDTO[]>(`${this.url}/listar`, { headers: this.getHeaders() });
  }

  listarPorId(id: number) {
    return this.http.get<AlertsInsertDTO>(`${this.url}/listar/${id}`, { headers: this.getHeaders() });
  }

  insertar(dto: AlertsInsertDTO) {
    return this.http.post<AlertsInsertDTO>(`${this.url}/insertar`, dto, { headers: this.getHeaders() });
  }

  actualizar(id: number, dto: AlertsInsertDTO) {
    return this.http.put<ShowAlertsDTO>(`${this.url}/actualizar/${id}`, dto, { headers: this.getHeaders() });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.url}/eliminar/${id}`, { headers: this.getHeaders(), responseType: 'text' });
  }

  // Este método es exclusivo de alertas, pero mantiene el mismo formato
  listarNoLeidas(idPaciente: number) {
    return this.http.get<ShowAlertsDTO[]>(`${this.url}/no-leidas/${idPaciente}`, { headers: this.getHeaders() });
  }


  getReporteTipos() {
    return this.http.get<any[]>(`${this.url}/reporte-tipos`, { headers: this.getHeaders() });
  }
}