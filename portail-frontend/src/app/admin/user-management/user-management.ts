import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserService, CreateUserRequest, UpdateUserRequest } from '../../core/services/user.service';
import { User } from '../../core/services/auth.service';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, DatePipe],
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
  selectedRole = '';
  
  showUserModal = false;
  editingUser: User | null = null;
  isSubmitting = false;
  
  userForm: FormGroup;

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
        this.users = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des utilisateurs:', error);
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

  onRoleFilterChange(): void {
    if (this.selectedRole) {
      this.userService.getUsersByRole(this.selectedRole).subscribe({
        next: (users) => {
          this.users = users;
          this.totalPages = 1;
          this.currentPage = 0;
        },
        error: (error) => {
          console.error('Erreur lors du filtrage par rôle:', error);
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
    if (this.userForm.valid) {
      this.isSubmitting = true;
      
      if (this.editingUser) {
        const updateData: UpdateUserRequest = {
          nom: this.userForm.get('nom')?.value,
          prenom: this.userForm.get('prenom')?.value,
          email: this.userForm.get('email')?.value,
          role: this.userForm.get('role')?.value
        };
        
        this.userService.updateUser(this.editingUser.id, updateData).subscribe({
          next: (updatedUser) => {
            const index = this.users.findIndex(u => u.id === updatedUser.id);
            if (index !== -1) {
              this.users[index] = updatedUser;
            }
            this.closeUserModal();
            this.isSubmitting = false;
          },
          error: (error) => {
            console.error('Erreur lors de la modification:', error);
            this.isSubmitting = false;
          }
        });
      } else {
        const createData: CreateUserRequest = {
          nom: this.userForm.get('nom')?.value,
          prenom: this.userForm.get('prenom')?.value,
          email: this.userForm.get('email')?.value,
          password: this.userForm.get('password')?.value,
          role: this.userForm.get('role')?.value
        };
        
        this.userService.createUser(createData).subscribe({
          next: (newUser) => {
            this.users.unshift(newUser);
            this.closeUserModal();
            this.isSubmitting = false;
            this.loadUsers();
          },
          error: (error) => {
            console.error('Erreur lors de la création:', error);
            this.isSubmitting = false;
          }
        });
      }
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