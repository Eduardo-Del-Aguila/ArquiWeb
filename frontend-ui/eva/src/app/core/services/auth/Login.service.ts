import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { JwtRequestDTO } from '../../models/JwtRequestDTO';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private http = inject(HttpClient)
  private platformId = inject(PLATFORM_ID)

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  login(request: JwtRequestDTO) {
    return this.http.post('http://localhost:8081/auth/login', request);
  }

  verificar(): boolean {

    if (!this.isBrowser()) {
      return false;
    }

    const token = sessionStorage.getItem('token');
    return token != null;
  }

  showRole(): string | null {

    if (!this.isBrowser()) {
      return null;
    }

    const token = sessionStorage.getItem('token');

    if (!token) {
      return null;
    }
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(token);
    return decodedToken.roles;
  }

  showMail(): string | null {

    if (!this.isBrowser()) {
      return null;
    }

    const token = sessionStorage.getItem('token');

    if (!token) {
      return null;
    }
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(token);


    return decodedToken.sub;
  }

}
