# 🏢 Portail Cabinet Comptable — Backend API & Frontend Angular

## 📋 **Description**

Application web complète pour la **digitalisation des échanges**, **centralisation de la communication**, **intégration IA pour l'analyse de documents**, et **tableau de bord administrateur** pour cabinet comptable.

---

## 🚀 Fonctionnalités Principales

### ✅ Authentification & Gestion Utilisateurs
- 🔐 **Inscription/Connexion** avec JWT
- 👥 **Gestion des rôles** (ADMIN, COMPTABLE, CLIENT)
- 📝 **Profils utilisateurs** complets
- 🔒 **Sécurité avancée** avec Spring Security

### ✅ Gestion des Documents
- 📄 **Upload de documents** (PDF, Word, Excel, etc.)
- 🔍 **Recherche avancée** par nom, type, contenu
- 📊 **Statuts de documents** (EN_ATTENTE, VALIDE, REJETE)
- 👤 **Gestion des propriétaires** et permissions

### ✅ Système de Messagerie
- 💬 **Messages en temps réel** entre utilisateurs
- 📧 **Conversations privées** avec historique
- 🔔 **Notifications de nouveaux messages**
- 📱 **Statuts de lecture** (ENVOYE, LU, SUPPRIME)

### ✅ Système de Notifications
- 🔔 **Notifications intelligentes** par type
- 📊 **Gestion des notifications non lues**
- 🎯 **Notifications système** et personnalisées
- 📅 **Historique des notifications**

### ✅ Intégration IA / ChatBot
- 🤖 **Analyse automatique de documents**
- 💭 **Questions/Réponses IA** intelligentes
- 📊 **Génération de rapports** automatiques
- 💡 **Suggestions d'amélioration** basées sur l'IA
- 🔍 **Recherche dans les conversations** IA

---

## 🛠️ Technologies Utilisées

### Backend Stack
- **Framework** : Spring Boot 3.2+
- **Language** : Java 17
- **Database** : PostgreSQL
- **Authentication** : JWT (JSON Web Token)
- **Security** : Spring Security
- **Build Tool** : Maven
- **API Design** : RESTful

### Architecture
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controllers   │    │     Services    │    │   Repositories  │
│   (6 modules)   │◄──►│   (6 modules)   │◄──►│   (6 modules)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      DTOs       │    │    Entities     │    │   PostgreSQL    │
│  (Request/Resp) │    │   (6 entités)   │    │   Database      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

---

## 📊 Statistiques du Projet

### 🎯 Progression : Backend terminé, Frontend cœur livré
- ✅ **Infrastructure** : 100%
- ✅ **Entités/Repositories** : 100%
- ✅ **Services** : 100%
- ✅ **Controllers** : 100% (6/6)
- ⏳ **Tests** : 30%

### **📈 Métriques**
- **6 Controllers** complets
- **6 Services** implémentés
- **6 Repositories** fonctionnels
- **6 Entités** optimisées
- **12+ DTOs** créés
- **50+ Endpoints** REST
- **2,875+ lignes** de code ajoutées

---

## 🚀 Installation & Démarrage

### Prérequis
- Java 17+
- PostgreSQL 12+
- Maven 3.6+

### 1. Cloner le Repository
```bash
git clone https://github.com/yasserbenhadda/portail-cabinet-comptable.git
cd portail-cabinet-comptable
```

### 2. Configuration Base de Données
```sql
CREATE DATABASE cabinet_portail;
```

### 3. Configuration Application
Modifier `src/main/resources/application.yml` :
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cabinet_portail
    username: votre_username
    password: votre_password
```

### 4. Lancement Backend
```bash
mvn spring-boot:run
```

### 5. Accès API
- **URL Base** : `http://localhost:8085`
- **Swagger UI** : `http://localhost:8085/swagger-ui/`
- **API Docs** : `http://localhost:8085/v3/api-docs/`

---

## 📚 Documentation API

### 🔐 Authentification
```http
POST /api/auth/register
POST /api/auth/login
GET /api/auth/profile
```

### 👥 Gestion Utilisateurs
```http
GET /api/users
GET /api/users/{id}
PUT /api/users/{id}
DELETE /api/users/{id}
```

### 📄 Gestion Documents
```http
POST /api/documents/upload
GET  /api/documents/user/{userId}       # Tous les documents liés à l'utilisateur (expéditeur OU destinataire)
GET  /api/documents/{id}
GET  /api/documents/download/{id}
PUT  /api/documents/{id}
DELETE /api/documents/{id}
```

### 💬 Messagerie
```http
POST /api/messages/send
GET /api/messages/user/{userId}
GET /api/messages/conversation/{userId1}/{userId2}
PUT /api/messages/{messageId}/read
```

### 🔔 Notifications
```http
POST /api/notifications/create
GET /api/notifications/user/{userId}
PUT /api/notifications/{notificationId}/read
GET /api/notifications/unread/{userId}
```

### 🤖 ChatBot IA
```http
POST /api/chatbot/analyze-document
POST /api/chatbot/ask-question
GET /api/chatbot/history/{userId}
POST /api/chatbot/generate-report
```

---

## 🧪 Tests API

### Postman Collections
Toutes les collections Postman sont disponibles dans le dossier `postman/` :

1. **Auth Collection** - Authentification
2. **Users Collection** - Gestion utilisateurs
3. **Documents Collection** - Gestion documents
4. **Messages Collection** - Messagerie
5. **Notifications Collection** - Notifications
6. **ChatBot Collection** - IA/ChatBot

### Exemple de Test
```bash
# Test d'inscription
curl -X POST http://localhost:8085/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@example.com",
    "password": "password123",
    "role": "CLIENT"
  }'
```

---

## 🔧 Configuration Avancée

### Variables d'Environnement
```bash
export SPRING_PROFILES_ACTIVE=dev
export DB_URL=jdbc:postgresql://localhost:5432/cabinet_portail
export DB_USERNAME=postgres
export DB_PASSWORD=password
export JWT_SECRET=votre_secret_jwt_tres_securise
```

### Profils Spring
- **dev** : Développement local
- **prod** : Production
- **test** : Tests

---

## 📁 Structure du Projet (Backend)

```
src/main/java/com/cabinet/portail/
├── config/                 # Configuration Spring
│   └── SecurityConfig.java
├── controller/             # Controllers REST
│   ├── AuthController.java
│   ├── UserController.java
│   ├── DocumentController.java
│   ├── MessageController.java
│   ├── NotificationController.java
│   └── ChatBotController.java
├── dto/                   # Data Transfer Objects
│   ├── request/           # DTOs pour les requêtes
│   └── response/          # DTOs pour les réponses
├── entity/                # Entités JPA
│   ├── User.java
│   ├── Document.java
│   ├── Message.java
│   ├── Notification.java
│   ├── ChatBotAI.java
│   └── Role.java
├── repository/            # Repositories JPA
│   ├── UserRepository.java
│   ├── DocumentRepository.java
│   ├── MessageRepository.java
│   ├── NotificationRepository.java
│   ├── ChatBotRepository.java
│   └── RoleRepository.java
├── service/               # Services métier
│   ├── AuthService.java
│   ├── UserService.java
│   ├── DocumentService.java
│   ├── MessageService.java
│   ├── NotificationService.java
│   ├── ChatBotService.java
│   └── impl/             # Implémentations
└── enums/                # Énumérations
    ├── UserRole.java
    ├── DocumentStatut.java
    ├── MessageStatut.java
    ├── NotificationType.java
    └── TypeAnalyse.java
```

---

## 🎯 **Fonctionnalités Avancées**

### **🔍 Recherche Intelligente**
- Recherche dans les documents
- Recherche dans les messages
- Recherche dans les notifications
- Recherche dans les conversations IA

### **📊 Pagination**
- Pagination des utilisateurs
- Pagination des documents
- Pagination des messages
- Pagination des notifications
- Pagination des conversations IA

### **🔔 Notifications Intelligentes**
- Notifications par type
- Notifications système
- Notifications personnalisées
- Gestion des notifications non lues

### **🤖 IA Intégrée**
- Analyse automatique de documents
- Questions/Réponses intelligentes
- Génération de rapports
- Suggestions d'amélioration
- Historique des conversations

---

## 🚀 Déploiement

### Docker (Recommandé)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/portail-cabinet-comptable-1.0.0.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Déploiement Manuel
```bash
mvn clean package
java -jar target/portail-cabinet-comptable-1.0.0.jar
```

---

## 🤝 Contribution

### Guidelines
1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

### Standards de Code
- Java 17
- Spring Boot 3.2+
- RESTful API design
- Validation des données
- Gestion des erreurs
- Documentation complète

---

## 📞 Support

### Contact
- **Email** : support@cabinet-comptable.com
- **GitHub Issues** : [Issues](https://github.com/yasserbenhadda/portail-cabinet-comptable/issues)

### Documentation
- **API Docs** : `http://localhost:8085/v3/api-docs/`
- **Swagger UI** : `http://localhost:8085/swagger-ui/`

---

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

---

## 🎉 Statut du Projet

### ✅ Backend 100% Terminé
- Architecture complète Spring Boot
- Authentification JWT sécurisée
- Gestion des utilisateurs et rôles
- Upload et gestion des documents
- Système de messagerie complet
- Système de notifications avancé
- Intégration IA/ChatBot (simulation)
- Validation des données
- Gestion des erreurs
- CORS configuré
- Pagination des résultats
- Recherche avancée

### 🔄 En Cours
- Tests unitaires complets
- Tests d'intégration
- Documentation API détaillée

### 📋 À Faire
- Intégration IA réelle (OpenAI, Claude)
- Frontend React/Vue.js
- Déploiement production
- Monitoring et logs
- Optimisations performance

---

*Dernière mise à jour : Décembre 2024*
*Version : 1.0.0*
*Statut : Backend 100% Terminé*

---

**🏢 Portail Cabinet Comptable**  
*Digitalisation • Communication • IA • Innovation*

---

## 🌐 Frontend Angular — Aperçu & Démarrage

### Fonctionnalités livrées
- Authentification JWT (formulaire de connexion, intercepteur HTTP)
- Redirection par rôle: ADMIN → `/dashboard/admin`, CLIENT → `/dashboard/client`
- Dashboard admin: liste de clients cliquables → page détail client (2 colonnes: Chat à gauche, Documents reçus + Envoi à droite)
- Dashboard client: actions rapides (Envoyer un document, Mes anciens documents, Documents reçus, Profil, Messages)
- Gestion des utilisateurs (CRUD) côté admin
- Messagerie admin ↔ client (conversation et envoi OK)
- Documents: upload client → tous les admins; upload admin → client; affichage via filtrage côté front basé sur `/api/documents/user/{userId}`

### Lancer le frontend
```bash
cd portail-frontend
npm install
npm start -- --port 4300 --no-open
```
Notes Windows PowerShell:
- Exécuter les commandes sur 2 lignes (éviter `&&`).
- Si le port 4200 est occupé, utiliser `--port 4300` comme ci-dessus.

### Routes principales (Angular)
- `/login`
- `/dashboard` (redirigé par garde de rôle)
- `/dashboard/admin`
- `/dashboard/client`
- `/admin/users`
- `/admin/client/:id`
- `/documents/upload`, `/documents/mine`, `/documents/received`
- `/messages`, `/profile`

### Intégration Documents — Note d’implémentation
Le backend expose `GET /api/documents/user/{userId}` (documents où l’utilisateur est expéditeur OU destinataire). Le frontend filtre localement:
- Envoyés par un utilisateur: comparer `expediteurId | expediteur.id | expediteur` avec l’id
- Reçus par un utilisateur: comparer `destinataireId | destinataire.id | destinataire` avec l’id
Affichage des dates: `dateUpload` ou `dateDepot` si présent.

### Dépannage
- Si les documents n’apparaissent pas, vérifier la forme JSON renvoyée par `/api/documents/user/{userId}` et que les clés `expediteur`/`destinataire` (ou leurs variantes) sont bien mappées.
