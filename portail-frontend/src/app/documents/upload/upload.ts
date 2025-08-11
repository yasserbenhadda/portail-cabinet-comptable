import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DocumentService } from '../../core/services/document.service';
import { AuthService } from '../../core/services/auth.service';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
  <div class="page-container">
    <h2>Envoyer un document</h2>

    <form [formGroup]="form" (ngSubmit)="submit()" class="upload-form">
      <div class="form-row">
        <label>Nom du document *</label>
        <input type="text" formControlName="nomFichier" placeholder="Ex: Facture Mars 2025" />
        <div class="error" *ngIf="form.get('nomFichier')?.invalid && form.get('nomFichier')?.touched">Nom obligatoire</div>
      </div>

      <div class="form-row">
        <label>Fichier *</label>
        <input type="file" (change)="onFileChange($event)" accept=".pdf,.doc,.docx,.xls,.xlsx,.png,.jpg,.jpeg" />
        <div class="error" *ngIf="fileError">{{ fileError }}</div>
      </div>

      <div class="form-row">
        <label>Description</label>
        <textarea rows="3" formControlName="description" placeholder="Décrivez le document (optionnel)"></textarea>
      </div>

      <button type="submit" class="btn-primary" [disabled]="isSubmitting || !selectedFile || form.invalid">{{ isSubmitting ? 'Envoi...' : 'Envoyer' }}</button>
      <div class="success" *ngIf="successMessage">{{ successMessage }}</div>
      <div class="error" *ngIf="errorMessage">{{ errorMessage }}</div>
    </form>
  </div>
  `,
  styles: [`
    .page-container{max-width:720px;margin:1.5rem auto;padding:1.25rem;background:#fff;border-radius:12px;box-shadow:0 10px 25px rgba(0,0,0,.06)}
    h2{margin:0 0 1rem 0;color:#2c3e50}
    .upload-form{display:flex;flex-direction:column;gap:1rem}
    .form-row{display:flex;flex-direction:column;gap:.5rem}
    label{font-weight:600;color:#495057}
    input[type="file"],input[type="text"],textarea{border:2px solid #e9edf2;border-radius:8px;padding:.65rem .75rem;font-size:.95rem}
    textarea{resize:vertical}
    .btn-primary{align-self:flex-start;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);color:#fff;border:none;border-radius:8px;padding:.6rem 1.1rem;cursor:pointer;transition:.2s}
    .btn-primary:hover{transform:translateY(-1px);box-shadow:0 8px 18px rgba(102,126,234,.35)}
    .error{color:#dc3545}
    .success{color:#1aa74a}
  `]
})
export class Upload {
  form: FormGroup;
  selectedFile: File | null = null;
  fileError: string | null = null;
  isSubmitting = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private documentService: DocumentService,
    private auth: AuthService,
    private userService: UserService
  ) {
    this.form = this.fb.group({
      nomFichier: ['', [Validators.required, Validators.minLength(2)]],
      description: ['']
    });
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];
      this.fileError = null;
    }
  }

  submit(): void {
    if (!this.selectedFile) {
      this.fileError = 'Veuillez sélectionner un fichier';
      return;
    }
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const expediteur = this.auth.getCurrentUser();
    if (!expediteur) {
      this.errorMessage = 'Session expirée. Veuillez vous reconnecter.';
      return;
    }

    this.isSubmitting = true;
    this.successMessage = null;
    this.errorMessage = null;

    // Récupérer tous les admins et envoyer le document à chacun
    this.userService.getAllAdmins().subscribe({
      next: (admins) => {
        if (!admins || admins.length === 0) {
          this.isSubmitting = false;
          this.errorMessage = 'Aucun administrateur trouvé.';
          return;
        }

        let completed = 0;
        let failed = 0;

        admins.forEach((admin) => {
          this.documentService.uploadDocument({
            file: this.selectedFile as File,
            expediteurId: expediteur.id,
            destinataireId: admin.id,
            description: this.form.get('description')?.value,
            nomFichierOverride: this.form.get('nomFichier')?.value
          }).subscribe({
            next: () => {
              completed++;
              if (completed + failed === admins.length) {
                this.isSubmitting = false;
                this.successMessage = failed === 0
                  ? 'Document envoyé à tous les administrateurs.'
                  : `Document envoyé (${completed}) administrateur(s), échec (${failed}).`;
                this.selectedFile = null;
                this.form.reset();
              }
            },
            error: () => {
              failed++;
              if (completed + failed === admins.length) {
                this.isSubmitting = false;
                this.successMessage = failed === 0
                  ? 'Document envoyé à tous les administrateurs.'
                  : `Document envoyé (${completed}) administrateur(s), échec (${failed}).`;
              }
            }
          });
        });
      },
      error: () => {
        this.isSubmitting = false;
        this.errorMessage = 'Impossible de récupérer la liste des administrateurs.';
      }
    });
  }
}
