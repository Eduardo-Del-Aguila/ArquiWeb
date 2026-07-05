import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthService } from './AuthService';
import { environment } from '../../env/environment';
import { MedicalHistoryInsertDTO, MedicalHistoryShowDTO } from '../interfaces/medicalHistory';

@Injectable({
  providedIn: 'root',
})
export class MedicalHistoryService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = `${environment.base_url}/historial-medico`;

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
  }

  listarTodos() {
    return this.http.get<MedicalHistoryShowDTO[]>(`${this.url}/historiales`, { headers: this.getHeaders() });
  }

  insertar(evaId: number, hospitalId: number, dto: MedicalHistoryInsertDTO) {
    return this.http.post<MedicalHistoryShowDTO>(
      `${this.url}/insertar/eva/${evaId}/hospital/${hospitalId}`,
      dto,
      { headers: this.getHeaders() }
    );
  }



  actualizar(id: number, dto: MedicalHistoryShowDTO) {
    return this.http.put<MedicalHistoryShowDTO>(`${this.url}/actualizar/${id}`, dto, { headers: this.getHeaders() });
  }

  eliminar(id: number) {
    return this.http.delete(`${this.url}/eliminar/${id}`, { headers: this.getHeaders(), responseType: 'text' });
  }
}
