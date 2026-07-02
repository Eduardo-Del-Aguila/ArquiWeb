import { Routes } from '@angular/router';
import { securityGuard } from './core/guards/security.guard';
import { roleGuard } from './core/guards/rol-guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('../app/features/auth/login/login').then(m => m.Login),
  },
  {
    path:'layout',
    loadComponent: () => import('../app/pages/Layout/Layout').then( m => m.Layout),
    children: [
      {
        path:'pets',
        loadComponent: () => import('../app/pages/pet-page/pet-page').then(m => m.PetPage),
        canActivate: [securityGuard, roleGuard(['ADMIN', 'PATIENT'])],
      },
      {
        path:'rol',
        loadComponent: () => import('./pages/RolPage/rol-page').then(m => m.RolPage)
      },
      {
        path:'users',
        loadComponent: () => import('./pages/users-page/users-page').then(m => m.UsersPage)
      },

    ]
  },
  {
    path: 'home',
    loadComponent: () => import('../app/landing-page/landing').then(m => m.Landing),
  },
  {
    path: '**',
    redirectTo: 'home',
  },

];
