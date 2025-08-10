import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Dashboard } from '../dashboard';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, Dashboard],
  template: `<app-dashboard></app-dashboard>`
})
export class AdminDashboard {}
