export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  nom: string;
  email: string;
  password: string;
  role: string;
}

export interface JwtResponse {
  token: string;
  type: string;
  id: number;
  nom: string;
  email: string;
  role: string;
}

export interface User {
  id: number;
  nom: string;
  email: string;
  role: string;
  createdAt: string;
  updatedAt: string;
} 