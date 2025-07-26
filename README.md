# 🏢 Portail Cabinet Comptable - Backend Spring Boot

## 📋 Table des Matières
1. [Vue d'Ensemble](#vue-densemble)
2. [Fonctionnalités](#fonctionnalités)
3. [Architecture Technique](#architecture-technique)
4. [Prérequis](#prérequis)
5. [Installation](#installation)
6. [Configuration](#configuration)
7. [Utilisation](#utilisation)
8. [API Documentation](#api-documentation)
9. [Structure du Projet](#structure-du-projet)
10. [Développement](#développement)
11. [Tests](#tests)
12. [Déploiement](#déploiement)
13. [Contribuer](#contribuer)
14. [Support](#support)

---

## 🎯 Vue d'Ensemble

Le **Portail Cabinet Comptable** est une application web moderne développée pour digitaliser les échanges entre un cabinet comptable et ses clients. Cette solution offre une plateforme sécurisée pour la gestion documentaire, la communication et l'analyse automatisée des documents.

### **Objectifs Principaux**
- ✅ **Digitaliser les échanges** entre le cabinet et ses clients
- ✅ **Centraliser la communication** et la gestion documentaire
- ✅ **Intégrer l'IA** pour l'analyse automatique des documents
- ✅ **Fournir un tableau de bord** administratif complet
- ✅ **Sécuriser les données** avec authentification JWT
- ✅ **Faciliter la collaboration** en temps réel

### **Technologies Utilisées**
- **Backend :** Spring Boot 3.2+, Java 17
- **Base de données :** PostgreSQL
- **Authentification :** JWT (JSON Web Tokens)
- **Sécurité :** Spring Security, BCrypt
- **Build :** Maven
- **Frontend :** Angular (développement séparé)

---

## 🚀 Fonctionnalités

### **🔐 Authentification & Autorisation**
- **Connexion sécurisée** avec JWT
- **Gestion des rôles** (Client, Comptable, Admin)
- **Refresh tokens** automatiques
- **Protection des endpoints** par rôle

### **👥 Gestion des Utilisateurs**
- **Inscription/Connexion** des clients
- **Profils utilisateurs** complets
- **Gestion des comptes** par les administrateurs
- **Recherche et filtrage** des utilisateurs

### **📁 Gestion Documentaire**
- **Upload sécurisé** de documents (PDF, images, Office)
- **Validation/Rejet** par les comptables
- **Historique complet** des documents
- **Recherche avancée** par nom, type, statut
- **Téléchargement sécurisé**

### **💬 Système de Messagerie**
- **Messages privés** entre utilisateurs
- **Conversations organisées**
- **Notifications temps réel**
- **Historique des échanges**

### **🔔 Notifications**
- **Notifications en temps réel**
- **Types multiples** (nouveau document, message, rappel)
- **Gestion lu/non-lu**
- **Préférences utilisateur**

### **🤖 IA ChatBot**
- **Analyse automatique** des documents
- **Extraction d'informations**
- **Questions/Réponses** sur les documents
- **Suggestions d'amélioration**

### **📊 Tableau de Bord Admin**
- **Statistiques complètes**
- **Gestion centralisée**
- **Monitoring des activités**
- **Rapports détaillés**

---

## 🏗️ Architecture Technique

### **Architecture en Couches**
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

### **Technologies Backend**
- **Spring Boot 3.2+** : Framework principal
- **Java 17** : Langage de programmation
- **Spring Security** : Sécurité et authentification
- **Spring Data JPA** : Accès aux données
- **PostgreSQL** : Base de données relationnelle
- **JWT** : Authentification sans état
- **Maven** : Gestion des dépendances

---

## 📋 Prérequis

### **Système**
- **Java 17** ou supérieur
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Git**

### **Outils de Développement**
- **IDE :** IntelliJ IDEA, Eclipse, VS Code
- **Extensions Java :** Extension Pack for Java
- **Base de données :** pgAdmin, DBeaver
- **API Testing :** Postman, Insomnia

### **Système d'Exploitation**
- ✅ **Windows 10/11**
- ✅ **macOS 10.15+**
- ✅ **Linux (Ubuntu 20.04+)**

---

## 🚀 Installation

### **1. Cloner le Repository**
```bash
git clone https://github.com/votre-username/portail-cabinet-comptable.git
cd portail-cabinet-comptable
```

### **2. Configuration de la Base de Données**
```sql
-- Créer la base de données PostgreSQL
CREATE DATABASE portail_cabinet;

-- Créer un utilisateur (optionnel)
CREATE USER portail_user WITH PASSWORD 'votre_password';
GRANT ALL PRIVILEGES ON DATABASE portail_cabinet TO portail_user;
```

### **3. Configuration de l'Application**
```bash
# Copier le fichier de configuration
cp src/main/resources/application.yml.example src/main/resources/application.yml

# Modifier les paramètres de connexion
nano src/main/resources/application.yml
```

### **4. Compilation et Lancement**
```bash
# Compiler le projet
mvn clean compile

# Lancer l'application
mvn spring-boot:run

# Ou avec Maven wrapper
./mvnw spring-boot:run
```

### **5. Vérification**
- **Application :** http://localhost:8080
- **API Health Check :** http://localhost:8080/api/health
- **Base de données :** Vérifier la connexion

---

## ⚙️ Configuration

### **Configuration Principale (`application.yml`)**
```yaml
# Configuration du serveur
server:
  port: 8080
  servlet:
    context-path: /

# Configuration Spring
spring:
  # Base de données
  datasource:
    url: jdbc:postgresql://localhost:5432/portail_cabinet
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
    
  # JPA/Hibernate
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        
  # Fichiers multipart
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
      enabled: true

# Configuration JWT
jwt:
  secret: votre_secret_jwt_tres_securise_et_complexe_pour_la_production
  expiration: 86400000  # 24 heures
  refresh-expiration: 604800000  # 7 jours

# Configuration des fichiers
file:
  upload-dir: ./uploads/documents
  allowed-extensions: pdf,jpg,jpeg,png,gif,doc,docx,xls,xlsx,txt
  max-size: 10485760  # 10MB
```

### **Variables d'Environnement**
```bash
# Base de données
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=portail_cabinet
export DB_USERNAME=postgres
export DB_PASSWORD=password

# JWT
export JWT_SECRET=votre_secret_jwt_tres_securise
export JWT_EXPIRATION=86400000

# Upload
export UPLOAD_DIR=./uploads/documents
```

---

## 🔧 Utilisation

### **Démarrage Rapide**
```bash
# 1. Démarrer PostgreSQL
sudo systemctl start postgresql

# 2. Créer la base de données
psql -U postgres -c "CREATE DATABASE portail_cabinet;"

# 3. Lancer l'application
mvn spring-boot:run

# 4. Accéder à l'application
open http://localhost:8080
```

### **Commandes Maven Utiles**
```bash
# Compiler le projet
mvn clean compile

# Lancer les tests
mvn test

# Package JAR
mvn clean package

# Lancer avec profil spécifique
mvn spring-boot:run -Dspring.profiles.active=dev

# Analyser les dépendances
mvn dependency:tree
```

### **Profils de Configuration**
- **`dev`** : Développement (logs détaillés, base de test)
- **`prod`** : Production (optimisé, sécurisé)
- **`test`** : Tests unitaires et d'intégration

---

## 📚 API Documentation

### **Endpoints Principaux**

#### **🔐 Authentification**
```http
POST /api/auth/login          # Connexion utilisateur
POST /api/auth/register       # Inscription nouveau client
POST /api/auth/refresh        # Renouvellement token
POST /api/auth/logout         # Déconnexion
GET  /api/auth/validate       # Validation token
```

#### **👥 Utilisateurs**
```http
GET    /api/users/profile/{id}    # Profil utilisateur
PUT    /api/users/profile/{id}    # Mise à jour profil
GET    /api/users/clients         # Liste des clients
GET    /api/users/admins          # Liste des administrateurs
POST   /api/users/                # Créer utilisateur
DELETE /api/users/{id}            # Supprimer utilisateur
```

#### **📁 Documents**
```http
POST   /api/documents/upload      # Upload document
GET    /api/documents/download/{id} # Télécharger document
GET    /api/documents/user/{userId} # Documents utilisateur
PUT    /api/documents/{id}/validate # Valider document
PUT    /api/documents/{id}/reject  # Rejeter document
DELETE /api/documents/{id}         # Supprimer document
```

#### **💬 Messages**
```http
POST   /api/messages/send         # Envoyer message
GET    /api/messages/conversation/{userId1}/{userId2} # Conversation
GET    /api/messages/user/{userId} # Messages utilisateur
PUT    /api/messages/{id}/read    # Marquer comme lu
```

#### **🔔 Notifications**
```http
GET    /api/notifications/user/{userId} # Notifications utilisateur
PUT    /api/notifications/{id}/read     # Marquer comme lue
DELETE /api/notifications/{id}          # Supprimer notification
```

#### **🤖 ChatBot IA**
```http
POST   /api/chatbot/analyze       # Analyser document
POST   /api/chatbot/question      # Poser question
GET    /api/chatbot/history/{userId} # Historique IA
```

### **Exemples d'Utilisation**

#### **Connexion Utilisateur**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "client@example.com",
    "password": "password123"
  }'
```

#### **Upload Document**
```bash
curl -X POST http://localhost:8080/api/documents/upload \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "file=@document.pdf" \
  -F "expediteurId=1" \
  -F "destinataireId=2" \
  -F "description=Facture Q4 2024"
```

---

## 📁 Structure du Projet

```
portail-cabinet-comptable/
├── 📖 README.md                              # Documentation principale
├── 📋 pom.xml                                # Configuration Maven
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/cabinet/portail/
│   │   │   ├── 🏗️ entity/                    # Entités JPA
│   │   │   ├── 📦 repository/                # Repositories
│   │   │   ├── 📦 service/                   # Services métier
│   │   │   │   └── 📁 impl/                  # Implémentations
│   │   │   ├── 📦 controller/                # Controllers REST
│   │   │   ├── 📦 dto/                       # Objets de transfert
│   │   │   │   ├── 📁 request/               # DTOs d'entrée
│   │   │   │   └── 📁 response/              # DTOs de sortie
│   │   │   ├── 🔒 security/                  # Configuration sécurité
│   │   │   ├── ⚙️ config/                    # Configuration globale
│   │   │   ├── ⚠️ exception/                 # Gestion d'erreurs
│   │   │   ├── 🛠️ util/                      # Utilitaires
│   │   │   └── 🚀 PortailApplication.java    # Classe principale
│   │   └── 📁 resources/
│   │       ├── ⚙️ application.yml            # Configuration
│   │       ├── 📁 static/                    # Ressources statiques
│   │       └── 📁 templates/                 # Templates (si nécessaire)
│   └── 📁 test/
│       └── 📁 java/com/cabinet/portail/      # Tests unitaires
├── 📁 uploads/                               # Fichiers uploadés
├── 📁 logs/                                  # Fichiers de logs
└── 📁 Architecture et Rôle des Dossiers/     # Documentation technique
    ├── 📖 README.md
    ├── 📊 Diagramme_Architecture.md
    ├── ⚙️ Variables_et_Configurations.md
    └── 📋 INDEX.md
```

---

## 🛠️ Développement

### **Environnement de Développement**
```bash
# Cloner le projet
git clone <repository-url>
cd portail-cabinet-comptable

# Installer les dépendances
mvn clean install

# Lancer en mode développement
mvn spring-boot:run -Dspring.profiles.active=dev
```

### **Bonnes Pratiques**
- ✅ **Architecture en couches** respectée
- ✅ **Injection de dépendances** utilisée
- ✅ **Validation des données** systématique
- ✅ **Gestion d'erreurs** centralisée
- ✅ **Documentation du code** complète
- ✅ **Tests unitaires** pour chaque service

### **Conventions de Code**
- **Nommage :** camelCase pour les variables, PascalCase pour les classes
- **Packages :** com.cabinet.portail.*
- **Annotations :** Utiliser les annotations Spring appropriées
- **Documentation :** JavaDoc pour toutes les méthodes publiques

---

## 🧪 Tests

### **Types de Tests**
- **Tests unitaires** : Services et repositories
- **Tests d'intégration** : Controllers et API
- **Tests de sécurité** : Authentification et autorisation

### **Exécution des Tests**
```bash
# Tous les tests
mvn test

# Tests unitaires uniquement
mvn test -Dtest=*UnitTest

# Tests d'intégration
mvn test -Dtest=*IntegrationTest

# Tests avec couverture
mvn test jacoco:report
```

### **Exemple de Test**
```java
@Test
@DisplayName("Test de connexion utilisateur")
void testUserLogin() {
    // Given
    LoginRequest request = new LoginRequest("test@example.com", "password");
    
    // When
    JwtResponse response = authService.login(request);
    
    // Then
    assertThat(response).isNotNull();
    assertThat(response.getToken()).isNotEmpty();
    assertThat(response.getEmail()).isEqualTo("test@example.com");
}
```

---

## 🚀 Déploiement

### **Environnement de Production**
```bash
# Build pour production
mvn clean package -Pprod

# Variables d'environnement production
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=production-db-host
export DB_PASSWORD=secure-production-password
export JWT_SECRET=very-secure-jwt-secret

# Lancement
java -jar target/portail-cabinet-comptable-1.0.0.jar
```

### **Docker (Optionnel)**
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/portail-cabinet-comptable-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
# Build Docker
docker build -t portail-cabinet-comptable .

# Run Docker
docker run -p 8080:8080 portail-cabinet-comptable
```

### **Configuration Serveur**
- **Java 17** installé
- **PostgreSQL** configuré
- **Variables d'environnement** définies
- **Firewall** configuré (port 8080)
- **SSL/TLS** pour HTTPS

---

## 🤝 Contribuer

### **Processus de Contribution**
1. **Fork** le projet
2. **Créer** une branche feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** les changements (`git commit -m 'Add AmazingFeature'`)
4. **Push** vers la branche (`git push origin feature/AmazingFeature`)
5. **Ouvrir** une Pull Request

### **Standards de Code**
- **Formatage** : Respecter le style de code existant
- **Tests** : Ajouter des tests pour les nouvelles fonctionnalités
- **Documentation** : Mettre à jour la documentation si nécessaire
- **Commits** : Messages de commit clairs et descriptifs

### **Checklist avant Pull Request**
- [ ] Code compilé sans erreurs
- [ ] Tests passent
- [ ] Documentation mise à jour
- [ ] Code review effectuée
- [ ] Fonctionnalités testées

---

## 📞 Support

### **Documentation**
- **Documentation technique** : `Architecture et Rôle des Dossiers/`
- **API Documentation** : Swagger UI (http://localhost:8080/swagger-ui.html)
- **Code source** : Commentaires JavaDoc

### **Contact**
- **Issues** : [GitHub Issues](https://github.com/votre-username/portail-cabinet-comptable/issues)
- **Email** : support@cabinet-comptable.com
- **Documentation** : Consulter le dossier `Architecture et Rôle des Dossiers/`

### **FAQ**
**Q : Comment changer le port de l'application ?**
A : Modifier `server.port` dans `application.yml`

**Q : Comment configurer la base de données ?**
A : Voir la section Configuration > Base de données

**Q : Comment ajouter un nouveau rôle utilisateur ?**
A : Modifier l'enum `UserRole` et ajouter les permissions correspondantes

---

## 📄 Licence

Ce projet est sous licence **MIT**. Voir le fichier `LICENSE` pour plus de détails.

---

## 🙏 Remerciements

- **Spring Boot** pour le framework exceptionnel
- **PostgreSQL** pour la base de données robuste
- **Communauté Java** pour les bonnes pratiques
- **Équipe de développement** pour le travail collaboratif

---

## 📈 Roadmap

### **Version 1.1 (Prochaine)**
- [ ] Interface utilisateur Angular
- [ ] Notifications temps réel (WebSocket)
- [ ] Intégration IA avancée
- [ ] Rapports et statistiques

### **Version 1.2**
- [ ] Application mobile
- [ ] Intégration calendrier
- [ ] Workflow automatisé
- [ ] API publique

### **Version 2.0**
- [ ] Multi-tenant
- [ ] Intégration ERP
- [ ] Intelligence artificielle avancée
- [ ] Analytics avancés

---

*Développé avec ❤️ pour la digitalisation des cabinets comptables*

---

## 📊 Statut du Projet

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-blue)
![Maven](https://img.shields.io/badge/Maven-3.6+-red)
![License](https://img.shields.io/badge/License-MIT-yellow)

**Statut :** 🟢 En développement actif  
**Version :** 1.0.0  
**Dernière mise à jour :** 2024  
**Maintenu par :** Équipe de développement
