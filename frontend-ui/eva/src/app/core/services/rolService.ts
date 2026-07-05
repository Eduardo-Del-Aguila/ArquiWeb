import { inject, Injectable } from '@angular/core';
import { environment } from '../../env/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './AuthService';
import { RolInsertDTO, RolShowDTO } from '../interfaces/rol';

@Injectable({
  providedIn: 'root',
})
export class RolService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = `${environment.base_url}/rol`;

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  listar() {
    return this.http.get<RolShowDTO[]>(`${this.url}/listar`, { headers: this.getHeaders() });
  }

  listById(id: number) {
    return this.http.get<RolShowDTO>(`${this.url}/listar/${id}`, { headers: this.getHeaders() });
  }

  insertar(dto: RolInsertDTO) {
    return this.http.post<RolShowDTO>(`${this.url}/insertar`, dto, { headers: this.getHeaders() });
  }

  actualizar(id: number, dto: RolInsertDTO) {
    return this.http.put<RolShowDTO>(`${this.url}/actualizar/${id}`, dto, { headers: this.getHeaders() });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.url}/eliminar/${id}`, { headers: this.getHeaders(), responseType: 'text' });
  }
}
