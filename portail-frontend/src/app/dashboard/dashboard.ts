import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DashboardService, DashboardStats } from '../core/services/dashboard.service';
import { AuthService, User } from '../core/services/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../core/services/user.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
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
  clients: User[] = [];

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCurrentUser();
    this.loadDashboardData();
    this.loadClients();
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
      error: () => {
        this.isLoading = false;
      }
    });
  }

  private loadClients(): void {
    this.userService.getAllClients().subscribe({
      next: (list) => {
        console.log('Clients chargés:', list);
        this.clients = list || [];
      },
      error: (error) => {
        console.error('Erreur lors du chargement des clients:', error);
        this.clients = [];
      }
    });
  }

  logout(): void {
    this.authService.logout();
  }

  openClient(client: User): void {
    this.router.navigate(['/admin/client', client.id]);
  }

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
