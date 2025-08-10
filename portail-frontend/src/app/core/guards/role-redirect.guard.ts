import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class RoleRedirectGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean | UrlTree {
    if (this.authService.isAdmin()) {
      return this.router.createUrlTree(['/dashboard/admin']);
    }
    return this.router.createUrlTree(['/dashboard/client']);
  }
}
