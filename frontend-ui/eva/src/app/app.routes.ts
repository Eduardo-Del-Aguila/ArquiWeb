import { Routes } from '@angular/router';
import { securityGuard } from './core/guards/security.guard';
import { roleGuard } from './core/guards/rol-guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('../app/features/auth/login/login').then(m => m.Login),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('../app/features/auth/Register/Register').then(m => m.Register),
  },
  {
    path: 'layout',
    loadComponent: () =>
      import('../app/pages/Layout/Layout').then(m => m.Layout),
    children: [

      // PETS
      {
        path: 'pets',
        canActivate: [securityGuard, roleGuard(['ADMIN', 'PATIENT'])],
        children: [
          {
            path: 'eva',
            loadComponent: () =>
              import('../app/pages/pet-page/pet-page').then(m => m.PetPage),
          },
          {
            path: 'reporte',
            loadComponent: () =>
              import('../app/pages/pet-page/EvaPetReport/EvaPetReport').then(
                m => m.EvaPetReport
              ),
          },
        ],
      },

      // ROLES
      {
        path: 'rol',
        canActivate: [securityGuard, roleGuard(['ADMIN'])],
        loadComponent: () =>
          import('./pages/RolPage/rol-page').then(m => m.RolPage),
      },
      {
        path: 'rol/reportes',
        canActivate: [securityGuard, roleGuard(['ADMIN'])],
        loadComponent: () =>
          import('./pages/reports-page/reports-page').then(
            m => m.ReportsPage
          ),
      },

      // USERS
      {
        path: 'users',
        canActivate: [securityGuard, roleGuard(['ADMIN'])],
        children: [
          {
            path: 'list',
            loadComponent: () =>
              import('./pages/users-page/users-page').then(
                m => m.UsersPage
              ),
          },
          {
            path: 'report',
            loadComponent: () =>
              import('./pages/users-page/UserReport/UserReport').then(
                m => m.UserReport
              ),
          },
        ],
      },

      // HOSPITALS
      {
        path: 'hospitals',
        canActivate: [securityGuard, roleGuard(['ADMIN', 'DOCTOR'])],
        loadComponent: () =>
          import('./pages/hospitales-page/hospitales-page').then(
            m => m.HospitalesPage
          ),
      },

      // ALERTS
      {
        path: 'alerts',
        loadComponent: () =>
          import('./pages/alert-page/alert-page').then(
            m => m.AlertPage
          ),
      },
      {
        path: 'alerts/reportes',
        loadComponent: () =>
          import('./pages/reports-page/reports-page').then(
            m => m.ReportsPage
          ),
      },

      // MEDICAL HISTORY
      {
        path: 'medical-history',
        canActivate: [securityGuard, roleGuard(['ADMIN', 'DOCTOR'])],
        loadComponent: () =>
          import('./pages/MedicalHistory/MedicalHistory-page').then(
            m => m.MedicalHistory
          ),
      },

      // NUEVAS RUTAS
      {
        path: 'medications',
        loadComponent: () =>
          import('./pages/medication-page/medication-page').then(
            m => m.MedicationPage
          ),
      },
      {
        path: 'prescriptions',
        loadComponent: () =>
          import('./pages/prescription-page/prescription-page').then(
            m => m.PrescriptionPage
          ),
      },
      {
        path: 'prescription-medications',
        loadComponent: () =>
          import(
            './pages/prescription-medications-page/prescription-medications-page'
          ).then(m => m.PrescriptionMedicationsPage),
      },
      {
        path: 'medications-active',
        loadComponent: () =>
          import(
            './pages/report-medicationactive/report-medicationactive'
          ).then(m => m.ReportMedicationactive),
      },
      {
        path: 'recipes-patient',
        loadComponent: () =>
          import('./pages/recipes-patient-page/recipes-patient-page').then(
            m => m.RecipesPatientPage
          ),
      },
      {
        path: 'medications-used',
        loadComponent: () =>
          import('./pages/medications-use-page/medications-use-page').then(
            m => m.MedicationsUsePage
          ),
      },
    ],
  },
  {
    path: 'home',
    loadComponent: () =>
      import('../app/landing-page/landing').then(m => m.Landing),
  },
  {
    path: '**',
    redirectTo: 'home',
  },
];
