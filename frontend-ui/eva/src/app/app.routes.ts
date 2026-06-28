import { Routes } from '@angular/router';

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
        loadComponent: () => import('../app/pages/pet-page/pet-page').then(m => m.PetPage)
      },
      {
        path:'rol',
        loadComponent: () => import('./pages/RolPage/rol-page').then(m => m.RolPage)
      },

    ]
  },
  {
    path: 'home',
    loadComponent: () => import('../app/landing-page/landing').then(m => m.Landing)
  },
  {
    path: '**',
    redirectTo: 'home'
  },

];
