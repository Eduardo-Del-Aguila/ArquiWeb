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
        canActivate: [securityGuard, roleGuard(['ADMIN', 'PATIENT'])],
        children: [
          {
            path:'eva',
            loadComponent: () => import('../app/pages/pet-page/pet-page').then(m => m.PetPage),
          },
          {
            path:'reporte',
            loadComponent: () => import('../app/pages/pet-page/EvaPetReport/EvaPetReport').then(m => m.EvaPetReport),
          },
        ]

      },
      {
        path:'rol',
        loadComponent: () => import('./pages/RolPage/rol-page').then(m => m.RolPage),
        canActivate: [securityGuard, roleGuard(['ADMIN'])],
      },
      {
        path:'users',
        canActivate: [securityGuard, roleGuard(['ADMIN'])],
        children: [
          {
            path: 'list',
            loadComponent: () => import('./pages/users-page/users-page').then(m => m.UsersPage),
          },
          {
            path: 'report',
            loadComponent: () => import('./pages/users-page//UserReport/UserReport').then(m => m.UserReport),
          },

        ]
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
        path: 'medical-history',
        loadComponent: () => import('./pages/MedicalHistory/MedicalHistory-page').then(m => m.MedicalHistory)
      }

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
