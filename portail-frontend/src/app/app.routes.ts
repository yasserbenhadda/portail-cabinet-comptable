import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { UserManagement } from './admin/user-management/user-management';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { 
    path: 'admin/users', 
    component: UserManagement,
    // Ici vous pourrez ajouter un guard pour vérifier le rôle ADMIN
  },
  // Autres routes...
];
