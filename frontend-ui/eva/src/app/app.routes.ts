import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('../app/features/auth/login/login').then((m) => m.Login),
  },
  {
    path: 'layout',
    loadComponent: () => import('../app/pages/Layout/Layout').then((m) => m.Layout),
    children: [
      {
        path: 'pets',
        loadComponent: () => import('../app/pages/pet-page/pet-page').then((m) => m.PetPage),
      },
      {
        path: 'rol',
        loadComponent: () => import('./pages/RolPage/rol-page').then((m) => m.RolPage),
      },
      {
        path: 'medications',
        loadComponent: () =>
          import('./pages/medication-page/medication-page').then((m) => m.MedicationPage),
      },
      {
        path: 'prescriptions',
        loadComponent: () =>
          import('./pages/prescription-page/prescription-page').then((m) => m.PrescriptionPage),
      },
      {
        path: 'prescription-medications',
        loadComponent: () =>
          import('./pages/prescription-medications-page/prescription-medications-page').then(
            (m) => m.PrescriptionMedicationsPage,
          ),
      },
      {
        path: 'medications-active',
        loadComponent: () =>
          import('./pages/report-medicationactive/report-medicationactive').then(
            (m) => m.ReportMedicationactive,
          ),
      },
      {
        path: 'recipes-patient',
        loadComponent: () =>
          import('./pages/recipes-patient-page/recipes-patient-page').then(
            (m) => m.RecipesPatientPage,
          ),
      },
      {
        path: 'medications-used',
        loadComponent: () =>
          import('./pages/medications-use-page/medications-use-page').then(
            (m) => m.MedicationsUsePage,
          ),
      },
    ],
  },
  {
    path: 'home',
    loadComponent: () => import('../app/landing-page/landing').then((m) => m.Landing),
  },
  {
    path: '**',
    redirectTo: 'home',
  },
];
