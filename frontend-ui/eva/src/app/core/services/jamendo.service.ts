import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class JamendoService {
  
  private readonly BASE_URL = 'https://api.jamendo.com/v3.0';
  private readonly CLIENT_ID = '5f6d47cc'; // Tu ID real

  constructor(private http: HttpClient) { }

  getMusicaRelajante(): Observable<any> {
    // Usar HttpParams evita problemas con caracteres especiales en la URL
    const params = new HttpParams()
      .set('client_id', this.CLIENT_ID)
      .set('format', 'json')
      .set('tags', 'relax')
      .set('limit', '10');
    
    return this.http.get(`${this.BASE_URL}/tracks/`, { params });
  }
}