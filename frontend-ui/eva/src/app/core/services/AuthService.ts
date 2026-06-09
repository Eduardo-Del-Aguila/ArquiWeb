import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest, LoginResponse } from '../interfaces/auth.interface';
import { Observable, tap } from 'rxjs';
import { environment } from '../../env/environment';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  private url = base_url;

  login(dto: LoginRequest) {
    return this.http.post<LoginResponse>(this.url, dto).pipe(
      tap(res => localStorage.setItem('token', res.token))
    );
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }
}
