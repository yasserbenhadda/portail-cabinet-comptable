import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService, User } from '../../core/services/auth.service';

@Component({
  selector: 'app-client-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './client-dashboard.html',
  styleUrls: ['./client-dashboard.scss']
})
export class ClientDashboard implements OnInit {
  currentUser: User | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
  }

  logout(): void {
    this.authService.logout();
  }

  goToUpload(): void {
    this.router.navigate(['/documents/upload']);
  }

  goToMyDocuments(): void {
    this.router.navigate(['/documents/mine']);
  }

  goToReceived(): void {
    this.router.navigate(['/documents/received']);
  }

  goToProfile(): void {
    this.router.navigate(['/profile']);
  }

  goToMessages(): void {
    this.router.navigate(['/messages']);
  }
}
