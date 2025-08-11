import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Dashboard } from './dashboard/dashboard';
import { UserManagement } from './admin/user-management/user-management';
import { AuthGuard } from './core/guards/auth.guard';
import { AdminGuard } from './core/guards/admin.guard';
import { RoleRedirectGuard } from './core/guards/role-redirect.guard';
import { ClientDashboard } from './dashboard/client-dashboard/client-dashboard';
import { AdminDashboard } from './dashboard/admin-dashboard/admin-dashboard';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', component: Login },

  { path: 'dashboard', canActivate: [AuthGuard, RoleRedirectGuard], component: Dashboard },
  { path: 'dashboard/admin', canActivate: [AuthGuard, AdminGuard], loadComponent: () => Promise.resolve(AdminDashboard) },
  { path: 'dashboard/client', canActivate: [AuthGuard], loadComponent: () => Promise.resolve(ClientDashboard) },

  // Route directe pour la fiche client admin
  { path: 'admin/client/:id', canActivate: [AuthGuard, AdminGuard], loadComponent: () => import('./admin/client-detail/client-detail').then(m => m.ClientDetail) },

  { 
    path: 'admin', 
    canActivate: [AuthGuard, AdminGuard],
    children: [
      { path: 'users', component: UserManagement },
      { path: '', redirectTo: 'users', pathMatch: 'full' }
    ]
  },

  { path: 'documents/upload', canActivate: [AuthGuard], loadComponent: () => import('./documents/upload/upload').then(m => m.Upload) },
  { path: 'documents/mine', canActivate: [AuthGuard], loadComponent: () => import('./documents/mine/mine').then(m => m.Mine) },
  { path: 'documents/received', canActivate: [AuthGuard], loadComponent: () => import('./documents/received/received').then(m => m.Received) },

  { path: 'profile', canActivate: [AuthGuard], loadComponent: () => import('./profile/profile').then(m => m.Profile) },
  { path: 'messages', canActivate: [AuthGuard], loadComponent: () => import('./messages/messages').then(m => m.Messages) },

  { path: '**', redirectTo: '/dashboard' }
];
