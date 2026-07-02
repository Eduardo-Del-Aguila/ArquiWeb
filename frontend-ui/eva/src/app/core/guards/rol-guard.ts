import { inject } from '@angular/core';
import { Router, type CanActivateFn } from '@angular/router';
import { LoginService } from '../services/auth/Login.service';

export const rolGuard: CanActivateFn = (route, state) => {
  return true;
};
export const roleGuard = (rolesPermitidos: string[]): CanActivateFn => {
  return () => {
    const router = inject(Router);
    const loginService = inject(LoginService);

    const rol = loginService.showRole();

    if (rol && rolesPermitidos.includes(rol)) {
      return true;
    }

    router.navigate(['/layout/home']);
    return false;
  };
};
