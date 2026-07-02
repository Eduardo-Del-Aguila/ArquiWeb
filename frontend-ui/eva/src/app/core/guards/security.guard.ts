import { inject } from '@angular/core';
import { Router, type CanActivateFn } from '@angular/router';
import { LoginService } from '../services/auth/Login.service';

export const securityGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const loginService = inject(LoginService);

  if (loginService.verificar()) {
    return true;
  }

  router.navigate(['/layout/pets']);
  return false;
};
