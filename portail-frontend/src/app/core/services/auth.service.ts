import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

export interface User {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: 'ADMIN' | 'COMPTABLE' | 'CLIENT';
  dateCreation: string;
  actif: boolean;
}

export interface LoginRequest {
  email: string;
  password: string;
}

// Réponse backend réelle (JwtResponse)
export interface JwtResponse {
  token: string;
  type?: string;
  id: number;
  nom: string;
  email: string;
  role: 'ADMIN' | 'COMPTABLE' | 'CLIENT';
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8085/api';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'current_user';

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.initializeAuth();
  }

  private initializeAuth(): void {
    const token = this.getToken();
    const user = this.getCurrentUser();
    if (token && user) {
      this.currentUserSubject.next(user);
      this.isAuthenticatedSubject.next(true);
    }
  }

  login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.API_URL}/auth/login`, credentials)
      .pipe(
        tap(response => {
          // Mapper la réponse backend vers notre modèle User
          const mappedUser: User = {
            id: response.id,
            nom: response.nom,
            prenom: '',
            email: response.email,
            role: response.role,
            dateCreation: new Date().toISOString(),
            actif: true
          };

          this.setToken(response.token);
          this.setCurrentUser(mappedUser);
          this.currentUserSubject.next(mappedUser);
          this.isAuthenticatedSubject.next(true);
        })
      );
  }

  register(userData: Partial<User> & { password: string }): Observable<any> {
    return this.http.post(`${this.API_URL}/auth/register`, userData);
  }

  logout(): void {
    this.clearAuth();
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  private setCurrentUser(user: User): void {
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  }

  getCurrentUser(): User | null {
    const userStr = localStorage.getItem(this.USER_KEY);
    if (!userStr) return null;
    try {
      return JSON.parse(userStr) as User;
    } catch {
      // Nettoyer si corrompu
      localStorage.removeItem(this.USER_KEY);
      return null;
    }
  }

  private clearAuth(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.currentUserSubject.next(null);
    this.isAuthenticatedSubject.next(false);
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser();
    return user?.role === 'ADMIN';
  }

  isComptable(): boolean {
    const user = this.getCurrentUser();
    return user?.role === 'COMPTABLE';
  }

  isClient(): boolean {
    const user = this.getCurrentUser();
    return user?.role === 'CLIENT';
  }

  hasRole(role: string): boolean {
    const user = this.getCurrentUser();
    return user?.role === role;
  }

  refreshToken(): Observable<any> {
    return this.http.post(`${this.API_URL}/auth/refresh`, {});
  }
}
