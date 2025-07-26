# 📊 Diagrammes d'Architecture - Portail Cabinet Comptable

## 🏗️ Architecture Globale

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           PORTAL CABINET COMPTABLE                          │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐        │
│  │   Frontend      │    │   Backend       │    │   Base de       │        │
│  │   Angular       │◄──►│   Spring Boot   │◄──►│   Données       │        │
│  │   (UI/UX)       │    │   (API REST)    │    │   PostgreSQL    │        │
│  └─────────────────┘    └─────────────────┘    └─────────────────┘        │
│           │                       │                       │                │
│           │                       ▼                       │                │
│           │              ┌─────────────────┐              │                │
│           │              │   Système de    │              │                │
│           │              │   Fichiers      │              │                │
│           │              │   (Uploads)     │              │                │
│           │              └─────────────────┘              │                │
│           │                       │                       │                │
│           └───────────────────────┼───────────────────────┘                │
│                                   │                                        │
│                                   ▼                                        │
│                        ┌─────────────────┐                                 │
│                        │   IA ChatBot    │                                 │
│                        │   (Analyse)     │                                 │
│                        └─────────────────┘                                 │
└─────────────────────────────────────────────────────────────────────────────┘
```

## 🔄 Flux d'Authentification

```mermaid
sequenceDiagram
    participant C as Client Angular
    participant AC as AuthController
    participant AS as AuthService
    participant UR as UserRepository
    participant DB as PostgreSQL
    participant JWT as JWT Generator

    C->>AC: POST /api/auth/login
    AC->>AS: login(credentials)
    AS->>UR: findByEmail(email)
    UR->>DB: SELECT * FROM users WHERE email = ?
    DB-->>UR: User data
    UR-->>AS: User object
    AS->>AS: validatePassword(password)
    AS->>JWT: generateToken(user)
    JWT-->>AS: JWT token
    AS-->>AC: JwtResponse
    AC-->>C: HTTP 200 + JWT token
```

## 📁 Upload de Document

```mermaid
sequenceDiagram
    participant C as Client
    participant DC as DocumentController
    participant DS as DocumentService
    participant FS as File System
    participant DR as DocumentRepository
    participant DB as PostgreSQL

    C->>DC: POST /api/documents/upload
    DC->>DS: uploadDocument(file, expediteur, destinataire, description)
    DS->>DS: isAllowedFile(filename)
    DS->>DS: generateUniqueFilename()
    DS->>FS: saveFile(file, uniqueName)
    FS-->>DS: File saved
    DS->>DS: createDocumentEntity()
    DS->>DR: save(document)
    DR->>DB: INSERT INTO documents
    DB-->>DR: Document saved
    DR-->>DS: Document object
    DS-->>DC: Document
    DC-->>C: HTTP 200 + Document
```

## 💬 Système de Messagerie

```mermaid
sequenceDiagram
    participant U1 as User 1
    participant MC as MessageController
    participant MS as MessageService
    participant MR as MessageRepository
    participant NS as NotificationService
    participant U2 as User 2

    U1->>MC: POST /api/messages/send
    MC->>MS: sendMessage(message)
    MS->>MR: save(message)
    MR-->>MS: Message saved
    MS->>NS: createNotification(recipient)
    NS-->>MS: Notification created
    MS-->>MC: Message sent
    MC-->>U1: HTTP 200
    NS->>U2: Real-time notification
```

## 🏛️ Architecture des Couches

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              LAYER ARCHITECTURE                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                    PRESENTATION LAYER                              │   │
│  │  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐   │   │
│  │  │AuthController│ │UserController│ │DocController│ │MsgController│   │   │
│  │  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                       │
│                                    ▼                                       │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                     BUSINESS LAYER                                 │   │
│  │  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐   │   │
│  │  │ AuthService │ │ UserService │ │ DocService  │ │ MsgService  │   │   │
│  │  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                       │
│                                    ▼                                       │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                     DATA ACCESS LAYER                              │   │
│  │  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐   │   │
│  │  │UserRepository│ │DocRepository│ │MsgRepository│ │NotifRepository│   │   │
│  │  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                       │
│                                    ▼                                       │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                      DATABASE LAYER                                │   │
│  │                        PostgreSQL                                  │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────┘
```

## 🔐 Architecture de Sécurité

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           SECURITY ARCHITECTURE                            │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐        │
│  │   Client        │    │   Spring        │    │   JWT Token     │        │
│  │   (Angular)     │───►│   Security      │───►│   Validation    │        │
│  └─────────────────┘    └─────────────────┘    └─────────────────┘        │
│           │                       │                       │                │
│           │                       ▼                       │                │
│           │              ┌─────────────────┐              │                │
│           │              │   Role-Based    │              │                │
│           │              │   Access        │              │                │
│           │              │   Control       │              │                │
│           │              └─────────────────┘              │                │
│           │                       │                       │                │
│           └───────────────────────┼───────────────────────┘                │
│                                   │                                        │
│                                   ▼                                        │
│                        ┌─────────────────┐                                 │
│                        │   Protected      │                                 │
│                        │   Endpoints      │                                 │
│                        └─────────────────┘                                 │
└─────────────────────────────────────────────────────────────────────────────┘
```

## 📊 Structure de Base de Données

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        DATABASE SCHEMA                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐                    │
│  │    users    │    │  documents  │    │   messages  │                    │
│  │             │    │             │    │             │                    │
│  │ id (PK)     │    │ id (PK)     │    │ id (PK)     │                    │
│  │ email       │    │ nom_fichier │    │ expediteur  │                    │
│  │ mot_de_passe│    │ chemin_fich │    │ destinataire│                    │
│  │ nom         │    │ type        │    │ contenu     │                    │
│  │ prenom      │    │ statut      │    │ statut      │                    │
│  │ role        │    │ expediteur  │    │ date_envoi  │                    │
│  │ enabled     │    │ destinataire│    │ lu          │                    │
│  │ date_creation│   │ taille_fich │    └─────────────┘                    │
│  └─────────────┘    │ description │                                       │
│                     │ date_depot  │    ┌─────────────┐                    │
│                     └─────────────┘    │notifications│                    │
│                                        │             │                    │
│  ┌─────────────┐    ┌─────────────┐    │ id (PK)     │                    │
│  │  chatbot_ai │    │   roles     │    │ destinataire│                    │
│  │             │    │             │    │ type        │                    │
│  │ id (PK)     │    │ id (PK)     │    │ contenu     │                    │
│  │ user        │    │ nom         │    │ lu          │                    │
│  │ question    │    │ description │    │ date_creation│                   │
│  │ reponse     │    │ permissions │    └─────────────┘                    │
│  │ type_analyse│    └─────────────┘                                       │
│  │ date_creation│                                                          │
│  └─────────────┘                                                          │
└─────────────────────────────────────────────────────────────────────────────┘
```

## 🔄 Flux de Données

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           DATA FLOW                                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  1. REQUEST FLOW:                                                          │
│     Client → Controller → Service → Repository → Database                  │
│                                                                             │
│  2. RESPONSE FLOW:                                                         │
│     Database → Repository → Service → Controller → Client                  │
│                                                                             │
│  3. VALIDATION FLOW:                                                       │
│     Input → DTO Validation → Business Rules → Database Constraints         │
│                                                                             │
│  4. ERROR FLOW:                                                            │
│     Exception → Exception Handler → Error Response → Client                │
└─────────────────────────────────────────────────────────────────────────────┘
```

## 🎯 Points d'Intégration

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        INTEGRATION POINTS                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐        │
│  │   Frontend      │    │   Backend       │    │   External      │        │
│  │   Integration   │◄──►│   API Gateway   │◄──►│   Services      │        │
│  │                 │    │                 │    │                 │        │
│  │ • Angular HTTP  │    │ • REST Endpoints│    │ • Email Service │        │
│  │ • WebSocket     │    │ • JWT Auth      │    │ • File Storage  │        │
│  │ • Real-time     │    │ • CORS Config   │    │ • AI Services   │        │
│  └─────────────────┘    └─────────────────┘    └─────────────────┘        │
│           │                       │                       │                │
│           │                       ▼                       │                │
│           │              ┌─────────────────┐              │                │
│           │              │   Database      │              │                │
│           │              │   Integration   │              │                │
│           │              │                 │              │                │
│           │              │ • PostgreSQL    │              │                │
│           │              │ • JPA/Hibernate │              │                │
│           │              │ • Connection    │              │                │
│           │              │   Pool          │              │                │
│           │              └─────────────────┘              │                │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

*Diagrammes générés pour le projet Portail Cabinet Comptable - Version 1.0* 