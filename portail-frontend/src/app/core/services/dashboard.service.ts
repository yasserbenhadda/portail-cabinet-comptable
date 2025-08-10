import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DashboardStats {
  totalUsers: number;
  activeUsers: number;
  totalDocuments: number;
  pendingDocuments: number;
  totalClients: number;
  totalComptables: number;
  recentActivities: Activity[];
  monthlyStats: MonthlyStat[];
}

export interface Activity {
  id: number;
  type: 'LOGIN' | 'DOCUMENT_UPLOAD' | 'USER_CREATED' | 'DOCUMENT_PROCESSED';
  description: string;
  userId: number;
  userName: string;
  timestamp: string;
  details?: any;
}

export interface MonthlyStat {
  month: string;
  users: number;
  documents: number;
  revenue?: number;
}

export interface DocumentStats {
  total: number;
  pending: number;
  processed: number;
  rejected: number;
  byType: DocumentTypeStat[];
}

export interface DocumentTypeStat {
  type: string;
  count: number;
  percentage: number;
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private readonly API_URL = 'http://localhost:8085/api';

  constructor(private http: HttpClient) {}

  getDashboardStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.API_URL}/dashboard/stats`);
  }

  getDocumentStats(): Observable<DocumentStats> {
    return this.http.get<DocumentStats>(`${this.API_URL}/dashboard/documents`);
  }

  getRecentActivities(limit: number = 10): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${this.API_URL}/dashboard/activities?limit=${limit}`);
  }

  getMonthlyStats(year: number = new Date().getFullYear()): Observable<MonthlyStat[]> {
    return this.http.get<MonthlyStat[]>(`${this.API_URL}/dashboard/monthly?year=${year}`);
  }

  getUserActivity(userId: number): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${this.API_URL}/dashboard/user/${userId}/activity`);
  }

  getSystemHealth(): Observable<any> {
    return this.http.get(`${this.API_URL}/dashboard/health`);
  }
}
