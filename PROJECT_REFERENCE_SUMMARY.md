# 📋 **PORTAL CABINET COMPTABLE - RÉFÉRENCE PROJET**

## 🎯 **Vue d'Ensemble du Projet**

**Portail Cabinet Comptable** est une application web complète développée avec une architecture moderne et sécurisée, comprenant un backend Java Spring Boot et un frontend Angular.

---

## 🏗️ **Architecture Technique**

### **Backend Stack**
- **Java 17** - Langage de programmation principal
- **Spring Boot 3.2+** - Framework d'application
- **Maven** - Gestionnaire de dépendances
- **PostgreSQL** - Base de données relationnelle
- **JWT (JSON Web Token)** - Authentification sécurisée
- **Spring Security** - Sécurité et autorisation
- **Spring Data JPA** - Persistance des données

### **Frontend Stack**
- **Angular 20.1+** - Framework frontend moderne
- **Node.js & npm** - Environnement de développement
- **TypeScript** - Langage de programmation typé
- **Angular Material** - Bibliothèque de composants UI
- **Angular Router** - Système de routage
- **SCSS** - Préprocesseur CSS avancé
- **RxJS** - Programmation réactive

---

## 🚀 **Lancement du Backend (Spring Boot)**

### **Prérequis**
- Java 17+ installé
- Maven installé
- PostgreSQL configuré

### **Commandes de Lancement**
```bash
# Compilation et lancement
mvn clean install
mvn spring-boot:run

# Ou avec le JAR compilé
djava -jar target/portail-cabinet-comptable-0.0.1-SNAPSHOT.jar
```

**Port par défaut :** `8085`

---

## 🚀 **Lancement du Frontend (Angular)**

### **Prérequis**
- Node.js 18+ installé
- npm installé

### **Commandes de Lancement**
```bash
cd portail-frontend

# Installation des dépendances
npm install

# Lancement en mode développement (port alternatif recommandé)
npm start -- --port 4300 --no-open
```

**Port par défaut :** `4200`

---

## 🔐 **Sécurité et Authentification**

### **Système JWT**
- **Tokens d'accès** avec expiration configurable
- **Refresh tokens** pour renouvellement automatique
- **Intercepteurs HTTP** pour injection automatique des tokens
- **Guards de route** pour protection des composants

### **Gestion des Rôles**
- **ADMIN** : Accès complet à toutes les fonctionnalités
- **COMPTABLE** : Accès aux documents et clients
- **CLIENT** : Accès limité à ses propres données

### **Sécurité Implémentée**
- ✅ **Authentification obligatoire** pour toutes les routes protégées
- ✅ **Vérification des rôles** pour les sections administrateur
- ✅ **Intercepteur HTTP** pour gestion automatique des tokens
- ✅ **Protection des routes** avec guards Angular
- ✅ **Suppression du lien d'inscription public** (admin-only)

---

## 🏛️ **Structure du Projet Backend (Spring Boot)**

```
src/main/java/
├── com/portail/
│   ├── PortailCabinetComptableApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── JwtConfig.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── UserController.java
│   │   └── DashboardController.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── UserService.java
│   │   └── JwtService.java
│   ├── repository/
│   │   └── UserRepository.java
│   ├── model/
│   │   ├── User.java
│   │   └── Role.java
│   └── security/
│       ├── JwtAuthenticationFilter.java
│       └── UserDetailsServiceImpl.java
```

---

## 🏛️ **Structure du Projet Frontend (Angular)**

### **Architecture Modulaire**
```
portail-frontend/src/app/
├── core/                          # Services et composants partagés
│   ├── services/                  # Services métier
│   │   ├── auth.service.ts       # Service d'authentification
│   │   ├── user.service.ts       # Service de gestion des utilisateurs
│   │   └── dashboard.service.ts  # Service du tableau de bord
│   ├── guards/                    # Guards de sécurité
│   │   ├── auth.guard.ts         # Guard d'authentification
│   │   └── admin.guard.ts        # Guard administrateur
│   └── interceptors/             # Intercepteurs HTTP
│       └── auth.interceptor.ts   # Intercepteur d'authentification
├── auth/                          # Module d'authentification
│   └── login/                     # Composant de connexion
│       ├── login.ts              # Logique du composant
│       ├── login.html            # Template HTML
│       └── login.scss            # Styles SCSS
├── admin/                         # Module administrateur
│   └── user-management/           # Gestion des utilisateurs
│       ├── user-management.ts    # Logique du composant
│       ├── user-management.html  # Template HTML
│       └── user-management.scss  # Styles SCSS
├── dashboard/                     # Module tableau de bord
│   ├── dashboard.ts              # Logique du composant
│   ├── dashboard.html            # Template HTML
│   └── dashboard.scss            # Styles SCSS
├── shared/                        # Composants partagés (à créer)
├── app.routes.ts                  # Configuration des routes
├── app.config.ts                  # Configuration Angular
└── app.html                       # Template principal
```

---

## 🔧 **Composants et Fonctionnalités Développés**

### **1. Système d'Authentification (100% Terminé)**
- ✅ **Service d'authentification** avec gestion JWT
- ✅ **Intercepteur HTTP** pour injection automatique des tokens
- ✅ **Guards de sécurité** pour protection des routes
- ✅ **Gestion des rôles** et permissions
- ✅ **Page de connexion** sécurisée

### **2. Gestion des Utilisateurs (100% Terminé)**
- ✅ **Interface d'administration** complète
- ✅ **CRUD utilisateurs** (Création, Lecture, Mise à jour, Suppression)
- ✅ **Filtrage par rôle** et recherche
- ✅ **Pagination** des résultats
- ✅ **Gestion des statuts** (actif/inactif)
- ✅ **Modales de création/modification**

### **3. Tableau de Bord (100% Terminé)**
- ✅ **Statistiques en temps réel** (utilisateurs, documents, clients)
- ✅ **Cartes de métriques** avec design moderne
- ✅ **Activités récentes** avec icônes contextuelles
- ✅ **Actions rapides** pour administrateurs
- ✅ **Navigation principale** vers les modules
- ✅ **Design responsive** et animations

### **4. Services et Infrastructure (100% Terminé)**
- ✅ **Services métier** pour toutes les entités
- ✅ **Gestion des erreurs** et notifications
- ✅ **Configuration Angular** moderne
- ✅ **Intercepteurs HTTP** fonctionnels
- ✅ **Guards de route** opérationnels

---

## 🎨 **Interface Utilisateur et Design**

### **Design System**
- **Palette de couleurs** moderne et professionnelle
- **Gradients** pour les éléments d'accent
- **Ombres et élévations** pour la hiérarchie visuelle
- **Animations** fluides et transitions
- **Design responsive** pour tous les écrans

### **Composants UI**
- **Cartes de statistiques** avec icônes colorées
- **Tableaux** avec tri et filtrage
- **Modales** pour les formulaires
- **Boutons** avec états et animations
- **Formulaires** avec validation

---

## 🔄 **Configuration des Routes**

### **Routes Publiques**
- `/login` - Page de connexion

### **Routes Protégées (Authentification Requise)**
- `/dashboard` - Tableau de bord principal (redirigé selon rôle)
- `/profile` - Profil utilisateur

### **Routes Administrateur (Rôle ADMIN Requis)**
- `/admin/users` - Gestion des utilisateurs
- `/admin/client/:id` - Détail client (2 colonnes: Chat + Documents reçus/Envoi)

---

## 📊 **État d'Avancement du Projet**

### **Backend (Spring Boot)**
- **Authentification JWT** : ✅ 100%
- **Gestion des utilisateurs** : ✅ 100%
- **Sécurité et autorisation** : ✅ 100%
- **API REST** : ✅ 100%
- **Base de données** : ✅ 100%

### **Frontend (Angular)**
- **Architecture de base** : ✅ 100%
- **Système d'authentification** : ✅ 100%
- **Gestion des utilisateurs** : ✅ 100%
- **Tableau de bord** : ✅ 100%
- **Services et intercepteurs** : ✅ 100%
- **Guards de sécurité** : ✅ 100%

### **Fonctionnalités Globales**
- **Sécurité** : ✅ 100%
- **Interface utilisateur** : ✅ 100%
- **Responsive design** : ✅ 100%
- **Gestion des erreurs** : ✅ 100%

---

## 🚀 **Prochaines Étapes Recommandées**

### **Phase 1 : Fonctionnalités Métier (Priorité Haute)**
1. **Gestion des Documents**
   - Upload et téléchargement
   - Classification par type
   - Workflow de validation

2. **Gestion des Clients**
   - Profils clients complets
   - Historique des interactions
   - Documents associés

3. **Système de Messagerie**
   - Communication interne
   - Notifications automatiques
   - Historique des échanges

### **Phase 2 : Fonctionnalités Avancées (Priorité Moyenne)**
1. **Tableaux de Bord Spécialisés**
   - Vue comptable
   - Vue client
   - Rapports personnalisés

2. **Intégration IA**
   - Analyse automatique des documents
   - Suggestions intelligentes
   - Détection d'anomalies

3. **Système de Rapports**
   - Génération automatique
   - Export PDF/Excel
   - Planification des rapports

### **Phase 3 : Optimisations (Priorité Basse)**
1. **Performance**
   - Mise en cache
   - Lazy loading
   - Optimisation des requêtes

2. **Monitoring**
   - Logs avancés
   - Métriques de performance
   - Alertes automatiques

---

## 🛠️ **Outils et Technologies Utilisés**

### **Développement**
- **IDE** : IntelliJ IDEA, VS Code
- **Versioning** : Git, GitHub
- **Build** : Maven, npm
- **Tests** : JUnit, Jasmine (à implémenter)

### **Déploiement**
- **Conteneurisation** : Docker (à configurer)
- **CI/CD** : GitHub Actions (à configurer)
- **Hébergement** : Cloud (AWS/Azure/GCP) ou serveur dédié

---

## 📝 **Notes de Développement**

### **Dernières Modifications**
- ✅ Redirection par rôle (admin/client)
- ✅ Détail client: vue 2 colonnes (Chat + Documents reçus / Envoi)
- ✅ Chat admin ↔ client fonctionnel (conversation et envoi)
- ✅ Documents: upload et visibilité croisée via filtrage front sur `/api/documents/user/{userId}`
- ✅ Gestion utilisateurs simplifiée (colonnes inutiles retirées, actions éditer/supprimer)

### **Points d'Attention**
- CORS configuré pour le développement local
- JWT avec expiration configurable
- Validation des formulaires côté client et serveur
- Gestion d'erreurs centralisée
- Documents: le backend renvoie une vue unifiée sur `/api/documents/user/{userId}`. Le front filtre côté client:
  - Envoyés: comparer `expediteurId | expediteur.id | expediteur`
  - Reçus: comparer `destinataireId | destinataire.id | destinataire`
  - Dates: `dateUpload` ou `dateDepot`

---

## 🎉 **Conclusion**

Le **Portail Cabinet Comptable** dispose maintenant d'une base solide et moderne avec :
- **Architecture robuste** et évolutive
- **Sécurité renforcée** avec JWT et guards
- **Interface utilisateur** professionnelle et responsive
- **Fonctionnalités d'administration** complètes
- **Code maintenable** et bien structuré

Le projet est prêt pour la phase de développement des fonctionnalités métier spécifiques au cabinet comptable. 

---

## 🔄 Mise à jour Frontend (Dashboards par rôle, routes, UI)

- **Dashboards par rôle**:
  - `RoleRedirectGuard` redirige `/dashboard` vers:
    - `'/dashboard/admin'` pour les utilisateurs `ADMIN` (dashboard admin existant)
    - `'/dashboard/client'` pour les autres (nouveau dashboard client)
  - `ClientDashboard`:
    - En-tête: « Bienvenue {Prénom Nom} »
    - Bouton rouge « Déconnexion » (logout)
    - Icônes centrées avec style chic: « Uploader un document », « Mes anciens documents », « Documents reçus »
    - Icônes bas: « Mon profil », « Messages »

- **Routes**:
  - `/dashboard` (AuthGuard + RoleRedirectGuard)
  - `/dashboard/admin` (AuthGuard + AdminGuard)
  - `/dashboard/client` (AuthGuard)
  - Documents: `/documents/upload`, `/documents/mine`, `/documents/received`
  - Profil: `/profile` — Messages: `/messages`

- **Sécurité / Auth**:
  - `AuthInterceptor` (fonctionnel) injecte le JWT sur toutes les requêtes
  - `AuthService.login` adapté au format backend `JwtResponse` (token + id/nom/email/role)
  - Page `Login` branchée en Reactive Forms avec gestion d’erreurs (serveur indisponible, 401, etc.)

- **Styles UI**:
  - Amélioration du style du dashboard client (dégradés doux, ombres, arrondis, centrage des icônes)
  - Bouton « Déconnexion » rouge dans l’en-tête

- **Arborescence ajoutée**:
  - `core/guards/role-redirect.guard.ts`
  - `dashboard/client-dashboard/` (html/ts/scss)
  - `dashboard/admin-dashboard/admin-dashboard.ts`
  - `documents/{upload|mine|received}/`
  - `profile/profile.ts`, `messages/messages.ts` 