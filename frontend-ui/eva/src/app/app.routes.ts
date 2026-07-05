import { Routes } from '@angular/router';
import { securityGuard } from './core/guards/security.guard';
import { roleGuard } from './core/guards/rol-guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('../app/features/auth/login/login').then(m => m.Login),
  },
  {
    path: 'register',
    loadComponent: () => import('../app/features/auth/Register/Register').then(m => m.Register),
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
        loadComponent: () => import('./pages/RolPage/rol-page').then(m => m.RolPage),
        canActivate: [securityGuard, roleGuard(['ADMIN'])],
      },
      {
        path: 'rol/reportes',
        // Asegúrate de que la ruta al archivo y el nombre del componente (ReportsPage) coincidan con el que creaste
        loadComponent: () => import('./pages/reports-page/reports-page').then(m => m.ReportsPage),
        canActivate: [securityGuard, roleGuard(['ADMIN'])],
      },
      {
        path:'users',
        loadComponent: () => import('./pages/users-page/users-page').then(m => m.UsersPage),
        canActivate: [securityGuard, roleGuard(['ADMIN'])],
      },
      {
        path:'hospitals',
        loadComponent: () => import('./pages/hospitales-page/hospitales-page').then(m => m.HospitalesPage),
        canActivate: [securityGuard, roleGuard(['ADMIN', 'DOCTOR'])],
      },
      {
        path: 'alerts',
        loadComponent: () => import('./pages/alert-page/alert-page').then(m => m.AlertPage)
      },
      {
        path: 'alerts/reportes',
        // Asegúrate de que la ruta al archivo apunte a la página de reportes que creaste
        loadComponent: () => import('./pages/reports-page/reports-page').then(m => m.ReportsPage)
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
