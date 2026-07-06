import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../env/environment';
import {
  PrescriptionMedicationInsert,
  PrescriptionMedicationShow
} from '../interfaces/prescription-medication.interface';
import { MedicationUse } from '../interfaces/medication-use.interface';
import { AuthService } from './AuthService';

@Injectable({
  providedIn: 'root'
})
export class PrescriptionMedication {

  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }
  private url = `${environment.base_url}/prescripcion-medicamentos`;

  listar() {
    return this.http.get<PrescriptionMedicationShow[]>(
      `${this.url}/listar`,
    { headers: this.getHeaders() }

    );
  }

  insertar(dto: PrescriptionMedicationInsert) {
    return this.http.post(
      `${this.url}/insertar`,
      dto,
    { headers: this.getHeaders() }

    );

  }

  listarPorId(id:number) {
    return this.http.get<PrescriptionMedicationInsert>(
      `${this.url}/listar/${id}`
    );
  }

  actualizar(id:number,dto:PrescriptionMedicationInsert) {
    return this.http.put(
      `${this.url}/actualizar/${id}`,
      dto,
      {responseType:'text'}
    );
  }

  eliminar(id:number) {
    return this.http.delete(
      `${this.url}/eliminar/${id}`,
      {responseType:'text'}
    );
  }
  mostUsedMedications() {

  return this.http.get<MedicationUse[]>(

    `${this.url}/medicamentos-mas-usados`,

    { headers: this.getHeaders() }

  );

}

}
