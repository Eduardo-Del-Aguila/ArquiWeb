import { inject, Injectable } from '@angular/core';
import { environment } from '../../env/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MedicationInsert, MedicationShow } from '../interfaces/medication.interface';
import { AuthService } from './AuthService';

const base_url = environment.base_url

@Injectable({
  providedIn: 'root',
})
export class Medication {

private http = inject(HttpClient);
  private authService = inject(AuthService);

  private url = `${environment.base_url}/medicamentos`;

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  listar() {
    return this.http.get<MedicationShow[]>(
      `${this.url}/listar`,
      //{ headers: this.getHeaders() }
    );
  }

  insertar(dto: MedicationInsert) {
    return this.http.post<MedicationShow>(
      `${this.url}/insertar`,
      dto,
      //{ headers: this.getHeaders() }
    );
  }

  listarPorId(id:number){
    return this.http.get<MedicationInsert>(
      `${this.url}/listar/${id}`,
      { headers:this.getHeaders()}
    );
  }

  actualizar(id:number,dto:MedicationInsert){
    return this.http.put(
      `${this.url}/actualizar/${id}`,
      dto,
      { headers:this.getHeaders(), responseType:'text'}
    );
  }

  eliminar(id:number){
    return this.http.delete(
      `${this.url}/eliminar/${id}`,
      {
        headers:this.getHeaders(),
        responseType:'text'
      }
    );
  }

  listarActivos(){
    return this.http.get<MedicationShow[]>(
      `${this.url}/medicamentos-activos`,
      { headers:this.getHeaders()}
    );
  }

}
