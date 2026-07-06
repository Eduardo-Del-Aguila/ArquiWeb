import { inject, Injectable } from '@angular/core';
import { environment } from '../../env/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PrescriptionInsert, PrescriptionShow } from '../interfaces/prescription.interface';
import { AuthService } from './AuthService';
import { RecipesPatient } from '../interfaces/recipes-patient.interface';

@Injectable({
  providedIn: 'root',
})
export class Prescription {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = `${environment.base_url}/prescripcion`;

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  listar() {
    return this.http.get<PrescriptionShow[]>(
      `${this.url}/listar`,
      { headers: this.getHeaders() }
    );
  }

  insertar(dto: PrescriptionInsert) {
    return this.http.post<PrescriptionShow>(
      `${this.url}/insertar`,
      dto,
      { headers: this.getHeaders() }
    );
  }

  listarPorId(id: number) {
    return this.http.get<PrescriptionInsert>(`${this.url}/listar/${id}`, {
      headers: this.getHeaders(),
    });
  }

  actualizar(id: number, dto: PrescriptionInsert) {
    return this.http.put(`${this.url}/actualizar/${id}`, dto, {
      headers: this.getHeaders(),
      responseType: 'text',
    });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.url}/eliminar/${id}`, {
      headers: this.getHeaders(),
      responseType: 'text',
    });
  }

  recipesPerPatient() {
  return this.http.get<RecipesPatient[]>(
    `${this.url}/recetas-por-paciente`,
    { headers: this.getHeaders() }
  );
}
}
