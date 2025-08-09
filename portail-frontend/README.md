# 🚀 Portail Cabinet Comptable - Frontend Angular

## 📋 Description

Frontend moderne développé avec Angular 17 pour le portail cabinet comptable. Interface utilisateur intuitive avec authentification JWT, design responsive et composants Material Design.

## 🎯 Fonctionnalités

### ✅ **Authentification JWT**
- **Login** : Connexion sécurisée avec email/mot de passe
- **Register** : Inscription avec validation des champs
- **Gestion des tokens** : Stockage automatique et gestion des sessions
- **Protection des routes** : Redirection automatique selon l'état d'authentification

### 🎨 **Interface Utilisateur**
- **Design moderne** : Interface Material Design avec thème personnalisé
- **Responsive** : Adaptation mobile, tablette et desktop
- **Animations** : Transitions fluides et feedback utilisateur
- **Accessibilité** : Support des lecteurs d'écran et navigation clavier

### 🔧 **Architecture Technique**
- **Angular 17** : Framework moderne avec standalone components
- **TypeScript** : Typage strict pour la robustesse du code
- **Reactive Forms** : Validation en temps réel des formulaires
- **RxJS** : Gestion des observables et des états asynchrones

## 🛠️ Technologies

| Technologie | Version | Rôle |
|-------------|---------|------|
| **Angular** | 17.0.0 | Framework principal |
| **TypeScript** | 5.2.0 | Langage de développement |
| **Angular Material** | 17.0.0 | Composants UI |
| **RxJS** | 7.8.0 | Gestion des observables |
| **SCSS** | - | Préprocesseur CSS |

## 📁 Structure du Projet

```
src/
├── app/
│   ├── auth/                    # Module d'authentification
│   │   ├── login/              # Composant de connexion
│   │   └── register/           # Composant d'inscription
│   ├── dashboard/              # Tableau de bord principal
│   ├── models/                 # Interfaces TypeScript
│   │   └── auth.model.ts      # Modèles d'authentification
│   ├── services/               # Services métier
│   │   └── auth.service.ts    # Service d'authentification
│   ├── app.component.ts        # Composant racine
│   ├── app.routes.ts          # Configuration des routes
│   └── app.config.ts          # Configuration de l'app
├── assets/                     # Ressources statiques
└── styles.scss                # Styles globaux
```

## 🚀 Installation et Démarrage

### **Prérequis**
- Node.js 18+ 
- npm ou yarn
- Angular CLI

### **Installation**
```bash
# Cloner le projet
git clone <repository-url>
cd portail-frontend

# Installer les dépendances
npm install

# Démarrer le serveur de développement
npm start
# ou
ng serve
```

### **Configuration**
L'application se connecte automatiquement au backend Spring Boot sur `http://localhost:8085`.

## 🔐 Authentification

### **Endpoints utilisés**
- `POST /api/auth/login` - Connexion
- `POST /api/auth/register` - Inscription

### **Gestion des tokens**
- Stockage automatique du JWT dans localStorage
- Injection automatique dans les headers HTTP
- Gestion de l'expiration et déconnexion

## 🎨 Composants UI

### **Login Component**
- Formulaire de connexion avec validation
- Gestion des erreurs et feedback utilisateur
- Design responsive avec Material Design

### **Register Component**
- Formulaire d'inscription complet
- Validation des mots de passe
- Sélection du rôle (Client/Admin)

### **Dashboard Component**
- Affichage des informations utilisateur
- Navigation et déconnexion
- Interface de base pour les fonctionnalités futures

## 🔧 Scripts Disponibles

```bash
# Développement
npm start          # Démarre le serveur de développement
npm run build      # Compile pour la production
npm run test       # Lance les tests unitaires
npm run lint       # Vérifie le code avec ESLint

# Production
npm run build:prod # Build optimisé pour production
```

## 🌐 Déploiement

### **Build de production**
```bash
ng build --configuration production
```

### **Serveur de production**
```bash
# Installer serve globalement
npm install -g serve

# Servir les fichiers buildés
serve -s dist/portail-frontend -l 4200
```

## 🔗 Intégration Backend

### **Configuration API**
- **URL de base** : `http://localhost:8085/api`
- **CORS** : Configuré côté backend
- **Authentification** : JWT Bearer Token

### **Services implémentés**
- `AuthService` : Gestion complète de l'authentification
- Gestion automatique des tokens
- Intercepteurs HTTP pour les headers d'autorisation

## 📱 Responsive Design

### **Breakpoints**
- **Mobile** : < 480px
- **Tablet** : 480px - 768px
- **Desktop** : > 768px

### **Fonctionnalités**
- Adaptation automatique des composants
- Navigation optimisée mobile
- Formulaires adaptés aux écrans tactiles

## 🔒 Sécurité

### **Mesures implémentées**
- Validation côté client et serveur
- Protection contre les attaques XSS
- Gestion sécurisée des tokens JWT
- Redirection automatique des utilisateurs non authentifiés

## 🚧 Prochaines Étapes

### **Phase 2 - Gestion des Documents**
- [ ] Upload de documents
- [ ] Consultation et téléchargement
- [ ] Partage entre utilisateurs
- [ ] Catégorisation automatique

### **Phase 3 - Messagerie**
- [ ] Système de messagerie interne
- [ ] Notifications en temps réel
- [ ] Historique des conversations

### **Phase 4 - IA ChatBot**
- [ ] Intégration IA pour analyse de documents
- [ ] Assistant conversationnel
- [ ] Suggestions automatiques

## 🤝 Contribution

### **Standards de code**
- **TypeScript strict** : Configuration stricte activée
- **ESLint** : Règles de qualité du code
- **Prettier** : Formatage automatique
- **Conventional Commits** : Messages de commit standardisés

### **Tests**
- **Unit Tests** : Jasmine/Karma
- **E2E Tests** : Playwright (prévu)
- **Coverage** : Objectif 80%+

## 📞 Support

Pour toute question ou problème :
- **Issues GitHub** : Signalement des bugs
- **Documentation** : Wiki du projet
- **Email** : support@cabinet-comptable.com

---

**Développé avec ❤️ par l'équipe du Portail Cabinet Comptable**
