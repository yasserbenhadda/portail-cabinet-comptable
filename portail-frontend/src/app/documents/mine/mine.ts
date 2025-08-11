import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentService, UploadedDocument } from '../../core/services/document.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-docs-mine',
  standalone: true,
  imports: [CommonModule],
  template: `
  <div class="page-container">
    <h2>Mes documents envoyés</h2>
    <div *ngIf="docs.length === 0" class="no-docs">Aucun document envoyé.</div>
    <ul class="doc-list" *ngIf="docs.length > 0">
      <li *ngFor="let doc of docs" class="doc-item">
        <div class="doc-info">
          <div class="doc-title">{{ doc.nomFichier }}</div>
          <div class="doc-desc" *ngIf="doc.description">{{ doc.description }}</div>
          <div class="doc-date" *ngIf="doc.dateUpload || doc.dateDepot">Envoyé le {{ (doc.dateUpload || doc.dateDepot) | date:'dd/MM/yyyy HH:mm' }}</div>
          <div class="doc-recipient" *ngIf="doc.destinataire">Destinataire: {{ doc.destinataire.nom || doc.destinataire }}</div>
        </div>
        <div class="doc-actions">
          <button class="btn-download" (click)="downloadDocument(doc)">Télécharger</button>
        </div>
      </li>
    </ul>
  </div>
  `,
  styles: [`
    .page-container{max-width:800px;margin:1.5rem auto;padding:1rem;background:#fff;border-radius:12px;box-shadow:0 10px 25px rgba(0,0,0,.06)}
    h2{margin:0 0 1rem 0;color:#2c3e50}
    .no-docs{text-align:center;color:#6c7a89;font-style:italic;padding:2rem}
    .doc-list{list-style:none;padding:0;margin:0;display:flex;flex-direction:column;gap:0.75rem}
    .doc-item{background:#f8f9fb;border:1px solid #e9edf2;border-radius:10px;padding:1rem;display:flex;justify-content:space-between;align-items:center}
    .doc-info{flex:1}
    .doc-title{font-weight:600;color:#344767;margin-bottom:0.25rem}
    .doc-desc{color:#6c7a89;margin-bottom:0.25rem}
    .doc-date{font-size:0.85rem;color:#8e9aaf;margin-bottom:0.25rem}
    .doc-recipient{font-size:0.85rem;color:#667eea}
    .doc-actions{display:flex;gap:0.5rem}
    .btn-download{background:#28a745;color:#fff;border:none;border-radius:6px;padding:0.4rem 0.8rem;cursor:pointer;font-size:0.9rem}
  `]
})
export class Mine implements OnInit {
  docs: UploadedDocument[] = [];

  constructor(private documentService: DocumentService, private auth: AuthService) {}

  ngOnInit(): void {
    const user = this.auth.getCurrentUser();
    if (!user) return;
    
    // Utiliser la nouvelle méthode pour récupérer les documents envoyés
    this.documentService.listDocumentsByUser(user.id).subscribe({
      next: (all) => {
        this.docs = DocumentService.filterSentBy(user.id, all || []);
      },
      error: (error) => {
        console.error('Erreur chargement documents envoyés:', error);
        this.docs = [];
      }
    });
  }

  downloadDocument(doc: UploadedDocument): void {
    console.log('Téléchargement du document:', doc);
    // Implémenter le téléchargement si nécessaire
  }
}
