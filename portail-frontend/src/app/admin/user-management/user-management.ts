import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserService, CreateUserRequest, UpdateUserRequest } from '../../core/services/user.service';
import { User } from '../../core/services/auth.service';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './user-management.html',
  styleUrls: ['./user-management.scss']
})
export class UserManagement implements OnInit {
  users: User[] = [];
  currentPage = 0;
  totalPages = 0;
  totalElements = 0;
  pageSize = 10;
  
  searchQuery = '';
  
  showUserModal = false;
  editingUser: User | null = null;
  isSubmitting = false;
  
  userForm: FormGroup;
  
  // Messages de feedback
  uploadSuccess: string | null = null;
  uploadError: string | null = null;

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) {
    this.userForm = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      prenom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        console.log('Réponse du service:', response);
        if (response && response.content) {
          this.users = response.content;
          this.totalPages = response.totalPages || 1;
          this.totalElements = response.totalElements || response.content.length;
        } else if (Array.isArray(response)) {
          this.users = response;
          this.totalPages = 1;
          this.totalElements = response.length;
        } else {
          console.warn('Format de réponse inattendu:', response);
          this.users = [];
          this.totalPages = 1;
          this.totalElements = 0;
        }
      },
      error: (error) => {
        console.error('Erreur lors du chargement des utilisateurs:', error);
        this.users = [];
        this.totalPages = 1;
        this.totalElements = 0;
      }
    });
  }

  onSearch(): void {
    if (this.searchQuery.trim()) {
      this.userService.searchUsers(this.searchQuery).subscribe({
        next: (users) => {
          this.users = users;
          this.totalPages = 1;
          this.currentPage = 0;
        },
        error: (error) => {
          console.error('Erreur lors de la recherche:', error);
        }
      });
    } else {
      this.loadUsers();
    }
  }

  openCreateUserModal(): void {
    this.editingUser = null;
    this.userForm.reset();
    this.userForm.patchValue({
      role: 'CLIENT'
    });
    this.showUserModal = true;
  }

  editUser(user: User): void {
    this.editingUser = user;
    this.userForm.patchValue({
      nom: user.nom,
      prenom: user.prenom,
      email: user.email,
      role: user.role
    });
    this.userForm.get('password')?.clearValidators();
    this.userForm.get('password')?.updateValueAndValidity();
    this.showUserModal = true;
  }

  closeUserModal(): void {
    this.showUserModal = false;
    this.editingUser = null;
    this.userForm.reset();
    this.userForm.get('password')?.setValidators([Validators.required, Validators.minLength(6)]);
    this.userForm.get('password')?.updateValueAndValidity();
  }

  onSubmit(): void {
    if (this.userForm.invalid) return;
    
    this.isSubmitting = true;
    const formData = this.userForm.value;
    
    if (this.editingUser) {
      // Modification d'un utilisateur existant
      const updateRequest: UpdateUserRequest = {
        nom: formData.nom,
        prenom: formData.prenom,
        email: formData.email,
        role: formData.role
      };
      
      // Ne pas modifier le mot de passe si vide
      if (formData.password && formData.password.trim()) {
        updateRequest.password = formData.password;
      }
      
      this.userService.updateUser(this.editingUser.id, updateRequest).subscribe({
        next: () => {
          this.uploadSuccess = 'Utilisateur modifié avec succès.';
          this.closeUserModal();
          this.loadUsers();
          this.isSubmitting = false;
        },
        error: (error) => {
          console.error('Erreur lors de la modification:', error);
          this.uploadError = 'Erreur lors de la modification de l\'utilisateur.';
          this.isSubmitting = false;
        }
      });
    } else {
      // Création d'un nouvel utilisateur
      const createRequest: CreateUserRequest = {
        nom: formData.nom,
        prenom: formData.prenom,
        email: formData.email,
        password: formData.password,
        role: formData.role
      };
      
      this.userService.createUser(createRequest).subscribe({
        next: () => {
          this.uploadSuccess = 'Utilisateur créé avec succès.';
          this.closeUserModal();
          this.loadUsers();
          this.isSubmitting = false;
        },
        error: (error) => {
          console.error('Erreur lors de la création:', error);
          this.uploadError = 'Erreur lors de la création de l\'utilisateur.';
          this.isSubmitting = false;
        }
      });
    }
  }

  toggleUserStatus(user: User): void {
    if (user.actif) {
      this.userService.deactivateUser(user.id).subscribe({
        next: (updatedUser) => {
          const index = this.users.findIndex(u => u.id === updatedUser.id);
          if (index !== -1) {
            this.users[index] = updatedUser;
          }
        },
        error: (error) => {
          console.error('Erreur lors de la désactivation:', error);
        }
      });
    } else {
      this.userService.activateUser(user.id).subscribe({
        next: (updatedUser) => {
          const index = this.users.findIndex(u => u.id === updatedUser.id);
          if (index !== -1) {
            this.users[index] = updatedUser;
          }
        },
        error: (error) => {
          console.error('Erreur lors de l\'activation:', error);
        }
      });
    }
  }

  deleteUser(user: User): void {
    if (confirm(`Êtes-vous sûr de vouloir supprimer l'utilisateur ${user.prenom} ${user.nom} ?`)) {
      this.userService.deleteUser(user.id).subscribe({
        next: () => {
          this.users = this.users.filter(u => u.id !== user.id);
        },
        error: (error) => {
          console.error('Erreur lors de la suppression:', error);
        }
      });
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadUsers();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadUsers();
    }
  }
} 