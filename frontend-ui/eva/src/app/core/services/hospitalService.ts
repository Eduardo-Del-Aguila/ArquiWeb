import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthService } from './authService';
import { environment } from '../../env/environment';
import { HospitalInsert, HospitalShow } from '../interfaces/hospital.interface';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root',
})
export class HospitalService {

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
    return this.http.get<HospitalShow[]>(
      `${this.url}/hospital/listar`,
      { headers: this.getHeaders() }
    );
  }

  insertar(dto: HospitalInsert) {
    return this.http.post<HospitalShow>(
      `${this.url}/hospital/insertar`,
      dto,
      { headers: this.getHeaders() }
    );
  }

  eliminar(id: number) {
    return this.http.delete(
      `${this.url}/hospital/eliminar/${id}`,
      { headers: this.getHeaders() }
    );
  }
}