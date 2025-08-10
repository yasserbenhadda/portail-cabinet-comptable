import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DashboardService, DashboardStats } from '../core/services/dashboard.service';
import { AuthService, User } from '../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, DatePipe],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.scss']
})
export class Dashboard implements OnInit {
  stats: DashboardStats = {
    totalUsers: 0,
    activeUsers: 0,
    totalDocuments: 0,
    pendingDocuments: 0,
    totalClients: 0,
    totalComptables: 0,
    recentActivities: [],
    monthlyStats: []
  };

  currentUser: User | null = null;
  isAdmin = false;
  isLoading = true;

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCurrentUser();
    this.loadDashboardData();
  }

  private loadCurrentUser(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.isAdmin = this.authService.isAdmin();
  }

  private loadDashboardData(): void {
    this.isLoading = true;
    
    this.dashboardService.getDashboardStats().subscribe({
      next: (data) => {
        this.stats = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement du dashboard:', error);
        this.isLoading = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
  }

  refreshData(): void {
    this.loadDashboardData();
  }

  // Méthodes pour les actions rapides
  goToUserManagement(): void {
    this.router.navigate(['/admin/users']);
  }

  goToDocumentManagement(): void {
    this.router.navigate(['/admin/documents']);
  }

  goToSettings(): void {
    this.router.navigate(['/admin/settings']);
  }

  goToReports(): void {
    this.router.navigate(['/admin/reports']);
  }

  // Méthodes pour la navigation principale
  goToDocuments(): void {
    this.router.navigate(['/documents']);
  }

  goToProfile(): void {
    this.router.navigate(['/profile']);
  }

  goToMessages(): void {
    this.router.navigate(['/messages']);
  }

  goToCalendar(): void {
    this.router.navigate(['/calendar']);
  }
}
