import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './auth.service';

export interface CreateUserRequest {
  nom: string;
  prenom: string;
  email: string;
  password: string;
  role: 'ADMIN' | 'COMPTABLE' | 'CLIENT';
}

export interface UpdateUserRequest {
  nom?: string;
  prenom?: string;
  email?: string;
  role?: 'ADMIN' | 'COMPTABLE' | 'CLIENT';
  actif?: boolean;
}

export interface UserListResponse {
  content: User[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly API_URL = 'http://localhost:8085/api';

  constructor(private http: HttpClient) {}

  getUsers(page: number = 0, size: number = 10): Observable<UserListResponse> {
    return this.http.get<UserListResponse>(`${this.API_URL}/users?page=${page}&size=${size}`);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.API_URL}/users/${id}`);
  }

  createUser(userData: CreateUserRequest): Observable<User> {
    return this.http.post<User>(`${this.API_URL}/users`, userData);
  }

  updateUser(id: number, userData: UpdateUserRequest): Observable<User> {
    return this.http.put<User>(`${this.API_URL}/users/${id}`, userData);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/users/${id}`);
  }

  searchUsers(query: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.API_URL}/users/search?q=${query}`);
  }

  getUsersByRole(role: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.API_URL}/users/role/${role}`);
  }

  activateUser(id: number): Observable<User> {
    return this.http.put<User>(`${this.API_URL}/users/${id}/activate`, {});
  }

  deactivateUser(id: number): Observable<User> {
    return this.http.put<User>(`${this.API_URL}/users/${id}/deactivate`, {});
  }
}
