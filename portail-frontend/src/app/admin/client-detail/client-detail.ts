import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { DocumentService, UploadedDocument } from '../../core/services/document.service';
import { AuthService, User } from '../../core/services/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-client-detail',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
  <div class="page-container" *ngIf="clientId">
    <h2>Gestion du client</h2>

    <div class="two-column-layout">
      <!-- Colonne de gauche : Chat -->
      <div class="left-column">
        <section class="section">
          <h3>Chat</h3>
          <div class="chat-box">
            <div class="msg" *ngFor="let m of messages">
              <div class="msg-header">
                <span class="msg-sender">{{ m.expediteurId === currentUser?.id ? 'Vous' : 'Client' }}</span>
                <span class="msg-time">{{ m.dateEnvoi | date:'dd/MM HH:mm' }}</span>
              </div>
              <div class="msg-content">{{ m.contenu }}</div>
            </div>
            <div *ngIf="messages.length === 0" class="no-messages">Aucun message dans cette conversation.</div>
          </div>
          <form [formGroup]="chatForm" (ngSubmit)="sendMessage()" class="chat-form">
            <input type="text" placeholder="Votre message" formControlName="contenu" />
            <button class="btn-primary" type="submit" [disabled]="chatForm.invalid">Envoyer</button>
          </form>
        </section>
      </div>

      <!-- Colonne de droite : Documents -->
      <div class="right-column">
        <section class="section">
          <h3>Documents reçus</h3>
          <div *ngIf="clientDocs.length === 0" class="no-docs">Aucun document reçu de ce client.</div>
          <ul class="doc-list" *ngIf="clientDocs.length > 0">
            <li *ngFor="let doc of clientDocs" class="doc-item">
              <div class="doc-info">
                <div class="doc-title">{{ doc.nomFichier }}</div>
                <div class="doc-desc" *ngIf="doc.description">{{ doc.description }}</div>
                <div class="doc-date" *ngIf="doc.dateUpload || doc.dateDepot">Reçu le {{ (doc.dateUpload || doc.dateDepot) | date:'dd/MM/yyyy HH:mm' }}</div>
              </div>
              <div class="doc-actions">
                <button class="btn-download" (click)="downloadDocument(doc)">Télécharger</button>
              </div>
            </li>
          </ul>
        </section>

        <section class="section">
          <h3>Envoyer un document</h3>
          <form [formGroup]="uploadForm" (ngSubmit)="sendToClient()" class="upload-form">
            <div class="form-group">
              <label>Nom du fichier *</label>
              <input type="text" placeholder="Nom du fichier" formControlName="nom" />
            </div>
            <div class="form-group">
              <label>Description (optionnel)</label>
              <textarea rows="3" placeholder="Description du document" formControlName="description"></textarea>
            </div>
            <div class="form-group">
              <label>Fichier *</label>
              <input type="file" (change)="onFile($event)" accept=".pdf,.doc,.docx,.xls,.xlsx,.txt,.jpg,.jpeg,.png" />
            </div>
            <button class="btn-primary" type="submit" [disabled]="uploadForm.invalid || !file">Envoyer</button>
          </form>
          <div class="error" *ngIf="uploadError">{{ uploadError }}</div>
          <div class="success" *ngIf="uploadSuccess">{{ uploadSuccess }}</div>
        </section>
      </div>
    </div>
  </div>
  `,
  styles: [`
    .page-container{max-width:1400px;margin:1.5rem auto;padding:1.25rem;background:#fff;border-radius:12px;box-shadow:0 10px 25px rgba(0,0,0,.06)}
    h2{margin:0 0 1rem 0;color:#2c3e50;font-size:1.8rem}
    h3{color:#344767;margin:1rem 0 0.75rem 0;border-bottom:2px solid #e9edf2;padding-bottom:0.5rem}
    .two-column-layout{display:flex;gap:2rem;margin-top:1.5rem}
    .left-column{flex:1;min-width:0}
    .right-column{flex:1;min-width:0}
    .section{margin-bottom:2rem}
    .no-docs,.no-messages{text-align:center;color:#6c7a89;font-style:italic;padding:1rem}
    .doc-list{list-style:none;padding:0;margin:0;display:flex;flex-direction:column;gap:0.75rem}
    .doc-item{background:#f8f9fb;border:1px solid #e9edf2;border-radius:10px;padding:1rem;display:flex;justify-content:space-between;align-items:center}
    .doc-info{flex:1}
    .doc-title{font-weight:600;color:#344767;margin-bottom:0.25rem}
    .doc-desc{color:#6c7a89;margin-bottom:0.25rem}
    .doc-date{font-size:0.85rem;color:#8e9aaf}
    .doc-actions{display:flex;gap:0.5rem}
    .btn-download{background:#28a745;color:#fff;border:none;border-radius:6px;padding:0.4rem 0.8rem;cursor:pointer;font-size:0.9rem}
    .form-group{margin-bottom:1rem}
    .form-group label{display:block;margin-bottom:0.5rem;font-weight:500;color:#344767}
    .upload-form input[type=text],.upload-form textarea,.chat-form input{border:2px solid #e9edf2;border-radius:8px;padding:0.75rem;width:100%;box-sizing:border-box}
    .upload-form input[type=file]{border:2px solid #e9edf2;border-radius:8px;padding:0.5rem;width:100%;box-sizing:border-box}
    textarea{resize:vertical;min-height:80px}
    .btn-primary{background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);color:#fff;border:none;border-radius:8px;padding:0.75rem 1.5rem;cursor:pointer;font-weight:500;margin-top:0.5rem}
    .chat-box{max-height:400px;overflow:auto;border:2px solid #eef2f7;border-radius:10px;padding:1rem;margin-bottom:1rem;background:#f8f9fb}
    .msg{background:#fff;border:1px solid #e9edf2;border-radius:8px;padding:0.75rem;margin:0.5rem 0}
    .msg-header{display:flex;justify-content:space-between;margin-bottom:0.5rem;font-size:0.85rem}
    .msg-sender{font-weight:600;color:#667eea}
    .msg-time{color:#6c7a89}
    .msg-content{color:#344767;line-height:1.4}
    .chat-form{display:flex;gap:0.75rem;align-items:flex-end}
    .chat-form input{flex:1}
    .chat-form button{margin:0}
    .error{color:#dc3545;background:#f8d7da;border:1px solid #f5c6cb;border-radius:6px;padding:0.75rem;margin-top:0.75rem}
    .success{color:#155724;background:#d4edda;border:1px solid #c3e6cb;border-radius:6px;padding:0.75rem;margin-top:0.75rem}
    @media (max-width: 768px) {
      .two-column-layout{flex-direction:column}
      .left-column,.right-column{flex:none}
    }
  `]
})
export class ClientDetail implements OnInit {
  clientId!: number;
  currentUser: User | null = null;
  clientDocs: UploadedDocument[] = [];
  file: File | null = null;
  uploadForm!: FormGroup;
  uploadError: string | null = null;
  uploadSuccess: string | null = null;

  chatForm!: FormGroup;
  messages: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private docs: DocumentService,
    private auth: AuthService,
    private fb: FormBuilder,
    private http: HttpClient
  ) {
    this.uploadForm = this.fb.group({ 
      nom: ['', [Validators.required, Validators.minLength(2)]], 
      description: [''] 
    });
    this.chatForm = this.fb.group({ 
      contenu: ['', [Validators.required]] 
    });
  }

  ngOnInit(): void {
    this.currentUser = this.auth.getCurrentUser();
    this.clientId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadClientDocs();
    this.loadConversation();
  }

  loadClientDocs(): void {
    if (!this.currentUser) return;
    const adminId = this.currentUser.id;
    this.docs.listDocumentsByUser(this.clientId).subscribe({
      next: (all) => {
        const docs = all || [];
        this.clientDocs = docs.filter((d: any) => {
          const expId = d.expediteurId ?? d.expediteur?.id ?? d.expediteur;
          const destId = d.destinataireId ?? d.destinataire?.id ?? d.destinataire;
          return Number(expId) === Number(this.clientId) && Number(destId) === Number(adminId);
        });
      },
      error: (error) => {
        console.error('Erreur chargement documents:', error);
        this.clientDocs = [];
      }
    });
  }

  onFile(e: Event): void {
    const input = e.target as HTMLInputElement;
    if (input.files && input.files.length) this.file = input.files[0];
  }

  sendToClient(): void {
    if (!this.file || !this.currentUser || this.uploadForm.invalid) return;
    this.uploadError = null; this.uploadSuccess = null;
    
    this.docs.uploadDocument({
      file: this.file,
      expediteurId: this.currentUser.id,
      destinataireId: this.clientId,
      description: this.uploadForm.get('description')?.value || undefined,
      nomFichierOverride: this.uploadForm.get('nom')?.value || undefined
    }).subscribe({
      next: () => {
        this.uploadSuccess = 'Document envoyé au client avec succès.';
        this.file = null; 
        this.uploadForm.reset();
        this.loadClientDocs();
      },
      error: (error) => {
        console.error('Erreur envoi document:', error);
        this.uploadError = 'Erreur lors de l\'envoi du document.';
      }
    });
  }

  downloadDocument(doc: UploadedDocument): void {
    // Implémenter le téléchargement si nécessaire
    console.log('Téléchargement du document:', doc);
  }

  loadConversation(): void {
    if (!this.currentUser) return;
    this.http.get<any[]>(`http://localhost:8085/api/messages/conversation/${this.currentUser.id}/${this.clientId}`).subscribe({
      next: (list) => this.messages = list || [],
      error: (error) => {
        console.error('Erreur chargement conversation:', error);
        this.messages = [];
      }
    });
  }

  sendMessage(): void {
    if (!this.currentUser || this.chatForm.invalid) return;
    const body = {
      expediteurId: this.currentUser.id,
      destinataireId: this.clientId,
      sujet: 'Message',
      contenu: this.chatForm.get('contenu')?.value
    };
    this.http.post('http://localhost:8085/api/messages/send', body).subscribe({
      next: () => { 
        this.chatForm.reset(); 
        this.loadConversation(); 
      },
      error: (error) => {
        console.error('Erreur envoi message:', error);
      }
    });
  }
}
