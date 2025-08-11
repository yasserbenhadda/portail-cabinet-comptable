import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UploadedDocument {
  id: number;
  nomFichier: string;
  description?: string;
  proprietaireId?: number;
  statut?: string;
  dateDepot?: string;
  dateUpload?: string;
  expediteur?: any;
  destinataire?: any;
  expediteurId?: number;
  destinataireId?: number;
}

@Injectable({ providedIn: 'root' })
export class DocumentService {
  private readonly API_URL = 'http://localhost:8085/api';

  constructor(private http: HttpClient) {}

  uploadDocument(params: {
    file: File;
    expediteurId: number;
    destinataireId: number;
    description?: string;
    nomFichierOverride?: string;
  }): Observable<UploadedDocument> {
    const form = new FormData();
    form.append('file', params.file);
    form.append('expediteurId', String(params.expediteurId));
    form.append('destinataireId', String(params.destinataireId));
    if (params.description) form.append('description', params.description);
    if (params.nomFichierOverride) form.append('nomFichier', params.nomFichierOverride);

    return this.http.post<UploadedDocument>(`${this.API_URL}/documents/upload`, form);
  }

  // Backend expose /documents/user/{userId} (tous les documents de l'utilisateur)
  listDocumentsByUser(userId: number): Observable<UploadedDocument[]> {
    return this.http.get<UploadedDocument[]>(`${this.API_URL}/documents/user/${userId}`);
  }

  // Utilitaires côté front pour filtrer
  static filterSentBy(userId: number, docs: UploadedDocument[]): UploadedDocument[] {
    return (docs || []).filter((d: any) => {
      const expId = d.expediteurId ?? d.expediteur?.id ?? d.expediteur;
      return Number(expId) === Number(userId);
    });
  }

  static filterReceivedBy(userId: number, docs: UploadedDocument[]): UploadedDocument[] {
    return (docs || []).filter((d: any) => {
      const destId = d.destinataireId ?? d.destinataire?.id ?? d.destinataire;
      return Number(destId) === Number(userId);
    });
  }

  downloadDocument(documentId: number): Observable<Blob> {
    return this.http.get(`${this.API_URL}/documents/download/${documentId}`, { responseType: 'blob' });
  }
}
