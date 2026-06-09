import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '**',
    redirectTo: 'login'
  },
  {
    path: 'login',
    loadComponent: () => import('../app/features/auth/login/login').then(m => m.Login)
  },
  {
    path:'layout',
    children: [
      {
        path: '**',
        redirectTo: 'home',
      },
      {
        path: 'home',
        loadComponent: () => import('../app/')
      },

    ]
  }


];
