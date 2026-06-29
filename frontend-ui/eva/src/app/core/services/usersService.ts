import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthService } from './authService';
import { environment } from '../../env/environment';
import { UserInsertDTO, UserShowDTO } from '../interfaces/users.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = `${environment.base_url}/usuario`;

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
  }

  listar() {
    return this.http.get<UserShowDTO[]>(`${this.url}/listar`, { headers: this.getHeaders() });
  }

  listarPorId(id: number) {
    return this.http.get<UserShowDTO>(`${this.url}/listar/${id}`, { headers: this.getHeaders() });
  }

  insertar(formData: FormData) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
    return this.http.post<UserShowDTO>(`${this.url}/insertar`, formData, { headers });
  }

  actualizar(id: number, formData: FormData) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
    return this.http.put<UserShowDTO>(`${this.url}/actualizar/${id}`, formData, { headers });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.url}/eliminar/${id}`, { headers: this.getHeaders(), responseType: 'text' });
  }
}
