import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../core/services/auth.service';
import { UserService } from '../core/services/user.service';

@Component({
  selector: 'app-messages',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
  <div class="page-container">
    <h2>Messages</h2>
    
    <div class="chat-container">
      <div class="chat-box">
        <div class="msg" *ngFor="let m of messages">
          <div class="msg-header">
            <span class="msg-sender">{{ m.expediteurId === currentUser?.id ? 'Vous' : (m.expediteurNom || 'Admin') }}</span>
            <span class="msg-time">{{ m.dateEnvoi | date:'dd/MM HH:mm' }}</span>
          </div>
          <div class="msg-content">{{ m.contenu }}</div>
        </div>
        <div *ngIf="messages.length === 0" class="no-messages">Aucun message dans cette conversation.</div>
      </div>
      
      <form [formGroup]="chatForm" (ngSubmit)="sendMessage()" class="chat-form">
        <input type="text" placeholder="Votre message" formControlName="contenu" />
        <button class="btn-primary" type="submit" [disabled]="chatForm.invalid || isSending">{{ isSending ? 'Envoi...' : 'Envoyer' }}</button>
      </form>
    </div>
  </div>
  `,
  styles: [`
    .page-container{max-width:800px;margin:1.5rem auto;padding:1rem;background:#fff;border-radius:12px;box-shadow:0 10px 25px rgba(0,0,0,.06)}
    h2{margin:0 0 1rem 0;color:#2c3e50}
    .chat-container{display:flex;flex-direction:column;height:500px}
    .chat-box{flex:1;overflow:auto;border:2px solid #eef2f7;border-radius:10px;padding:1rem;margin-bottom:1rem;background:#f8f9fb;max-height:400px}
    .msg{background:#fff;border:1px solid #e9edf2;border-radius:8px;padding:0.75rem;margin:0.5rem 0}
    .msg-header{display:flex;justify-content:space-between;margin-bottom:0.5rem;font-size:0.85rem}
    .msg-sender{font-weight:600;color:#667eea}
    .msg-time{color:#6c7a89}
    .msg-content{color:#344767;line-height:1.4}
    .no-messages{text-align:center;color:#6c7a89;font-style:italic;padding:2rem}
    .chat-form{display:flex;gap:0.75rem;align-items:flex-end}
    .chat-form input{flex:1;border:2px solid #e9edf2;border-radius:8px;padding:0.75rem}
    .btn-primary{background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);color:#fff;border:none;border-radius:8px;padding:0.75rem 1.5rem;cursor:pointer;font-weight:500}
  `]
})
export class Messages implements OnInit {
  currentUser: any = null;
  messages: any[] = [];
  chatForm: FormGroup;
  isSending = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private auth: AuthService,
    private userService: UserService
  ) {
    this.chatForm = this.fb.group({
      contenu: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.currentUser = this.auth.getCurrentUser();
    this.loadMessages();
  }

  loadMessages(): void {
    if (!this.currentUser) return;
    this.http.get<any[]>(`http://localhost:8085/api/messages/user/${this.currentUser.id}`).subscribe({
      next: (list) => {
        this.messages = list || [];
      },
      error: () => {
        this.messages = [];
      }
    });
  }

  sendMessage(): void {
    if (!this.currentUser || this.chatForm.invalid) return;
    const contenu = this.chatForm.get('contenu')?.value;
    this.isSending = true;

    // Envoyer le message à tous les admins
    this.userService.getAllAdmins().subscribe({
      next: (admins) => {
        if (!admins || admins.length === 0) { this.isSending = false; return; }
        let done = 0, fail = 0;
        admins.forEach(admin => {
          const body = {
            expediteurId: this.currentUser.id,
            destinataireId: admin.id,
            sujet: 'Message',
            contenu
          };
          this.http.post('http://localhost:8085/api/messages/send', body).subscribe({
            next: () => { done++; if (done + fail === admins.length) this.finishSend(); },
            error: () => { fail++; if (done + fail === admins.length) this.finishSend(); }
          });
        });
      },
      error: () => {
        this.isSending = false;
      }
    });
  }

  private finishSend(): void {
    this.isSending = false;
    this.chatForm.reset();
    this.loadMessages();
  }
}
