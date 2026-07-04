import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest, LoginResponse } from '../interfaces/auth.interface';
import { Observable, tap } from 'rxjs';
import { environment } from '../../env/environment';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  private url = 'http://localhost:8081/auth/login';

  login(dto: LoginRequest) {
    return this.http.post<LoginResponse>(this.url, dto).pipe(
      tap(res => {
        sessionStorage.setItem('token', res.jwttoken);
      })
    );
  }

  logout() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return !!sessionStorage.getItem('token');
  }
}
