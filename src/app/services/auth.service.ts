import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginRequest, RegisterRequest, JwtResponse, User } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8085/api';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  login(loginRequest: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.API_URL}/auth/login`, loginRequest)
      .pipe(
        tap(response => {
          this.storeToken(response.token);
          this.storeUser(response);
        })
      );
  }

  register(registerRequest: RegisterRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.API_URL}/auth/register`, registerRequest)
      .pipe(
        tap(response => {
          this.storeToken(response.token);
          this.storeUser(response);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  private storeToken(token: string): void {
    localStorage.setItem('token', token);
  }

  private storeUser(jwtResponse: JwtResponse): void {
    const user: User = {
      id: jwtResponse.id,
      nom: jwtResponse.nom,
      email: jwtResponse.email,
      role: jwtResponse.role,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString()
    };
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  private loadUserFromStorage(): void {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.currentUserSubject.next(user);
    }
  }
} 