# 🎯 Variables et Configurations - Portail Cabinet Comptable

## 📋 Table des Matières
1. [Configuration Application](#configuration-application)
2. [Variables d'Environnement](#variables-denvironnement)
3. [Statuts et Énumérations](#statuts-et-énumérations)
4. [Variables Métier](#variables-métier)
5. [Configuration Sécurité](#configuration-sécurité)
6. [Configuration Base de Données](#configuration-base-de-données)

---

## ⚙️ Configuration Application

### **`application.yml` - Configuration Principale**

```yaml
# Configuration du serveur
server:
  port: 8080
  servlet:
    context-path: /

# Configuration Spring
spring:
  # Configuration de la base de données
  datasource:
    url: jdbc:postgresql://localhost:5432/portail_cabinet
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
    
  # Configuration JPA/Hibernate
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        
  # Configuration des fichiers multipart
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
      enabled: true

# Configuration JWT
jwt:
  secret: votre_secret_jwt_tres_securise_et_complexe_pour_la_production
  expiration: 86400000  # 24 heures en millisecondes
  refresh-expiration: 604800000  # 7 jours en millisecondes

# Configuration des fichiers
file:
  upload-dir: ./uploads/documents
  allowed-extensions: pdf,jpg,jpeg,png,gif,doc,docx,xls,xlsx,txt
  max-size: 10485760  # 10MB en bytes

# Configuration de l'application
app:
  name: Portail Cabinet Comptable
  version: 1.0.0
  description: Portail digital pour cabinet comptable
```

---

## 🌍 Variables d'Environnement

### **Variables Système**

| Variable | Description | Valeur Par Défaut | Usage |
|----------|-------------|-------------------|-------|
| `SPRING_PROFILES_ACTIVE` | Profil Spring actif | `dev` | Environnement |
| `DB_HOST` | Hôte de la base de données | `localhost` | Connexion DB |
| `DB_PORT` | Port de la base de données | `5432` | Connexion DB |
| `DB_NAME` | Nom de la base de données | `portail_cabinet` | Connexion DB |
| `DB_USERNAME` | Utilisateur de la base | `postgres` | Connexion DB |
| `DB_PASSWORD` | Mot de passe de la base | `password` | Connexion DB |
| `JWT_SECRET` | Clé secrète JWT | `secret` | Authentification |
| `JWT_EXPIRATION` | Expiration JWT (ms) | `86400000` | Authentification |
| `UPLOAD_DIR` | Répertoire d'upload | `./uploads` | Stockage fichiers |

### **Variables Métier**

| Variable | Description | Valeur | Usage |
|----------|-------------|--------|-------|
| `MAX_FILE_SIZE` | Taille max fichier | `10MB` | Upload |
| `ALLOWED_FILE_TYPES` | Types autorisés | `pdf,jpg,doc` | Validation |
| `SESSION_TIMEOUT` | Timeout session | `30min` | Sécurité |
| `MAX_LOGIN_ATTEMPTS` | Tentatives max | `3` | Sécurité |
| `PASSWORD_MIN_LENGTH` | Longueur min mot de passe | `8` | Validation |

---

## 📊 Statuts et Énumérations

### **`UserRole` - Rôles Utilisateurs**

```java
public enum UserRole {
    CLIENT("Client du cabinet"),
    COMPTABLE("Comptable du cabinet"),
    ADMIN("Administrateur système");
    
    private final String description;
}
```

**Utilisation :**
- **CLIENT** : Accès limité aux documents et messages
- **COMPTABLE** : Accès complet aux documents et validation
- **ADMIN** : Accès complet + gestion utilisateurs

### **`DocumentStatut` - Statuts des Documents**

```java
public enum DocumentStatut {
    DEPOSE("Document déposé, en attente de traitement"),
    VALIDE("Document validé par le comptable"),
    REJETE("Document rejeté avec raison");
    
    private final String description;
}
```

**Workflow :**
```
DEPOSE → VALIDE (par comptable)
DEPOSE → REJETE (par comptable)
```

### **`MessageStatut` - Statuts des Messages**

```java
public enum MessageStatut {
    ENVOYE("Message envoyé"),
    RECU("Message reçu"),
    LU("Message lu par le destinataire");
    
    private final String description;
}
```

### **`NotificationType` - Types de Notifications**

```java
public enum NotificationType {
    NOUVEAU_DOCUMENT("Nouveau document déposé"),
    DOCUMENT_VALIDE("Document validé"),
    DOCUMENT_REJETE("Document rejeté"),
    NOUVEAU_MESSAGE("Nouveau message reçu"),
    RAPPEL("Rappel important");
    
    private final String description;
}
```

### **`TypeAnalyse` - Types d'Analyse IA**

```java
public enum TypeAnalyse {
    EXTRACTION_INFO("Extraction d'informations"),
    VERIFICATION_DOCUMENT("Vérification de document"),
    SUGGESTION("Suggestion d'amélioration"),
    QUESTION_REPONSE("Question/Réponse sur document");
    
    private final String description;
}
```

---

## 💼 Variables Métier

### **Limites et Contraintes**

```java
// Limites de fichiers
public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
public static final String[] ALLOWED_EXTENSIONS = {
    ".pdf", ".jpg", ".jpeg", ".png", ".gif", 
    ".doc", ".docx", ".xls", ".xlsx", ".txt"
};

// Limites de sécurité
public static final int MAX_LOGIN_ATTEMPTS = 3;
public static final int PASSWORD_MIN_LENGTH = 8;
public static final int SESSION_TIMEOUT_MINUTES = 30;

// Limites de données
public static final int MAX_DOCUMENTS_PER_USER = 1000;
public static final int MAX_MESSAGES_PER_CONVERSATION = 100;
public static final int MAX_NOTIFICATIONS_PER_USER = 500;
```

### **Messages d'Erreur**

```java
// Messages de validation
public static final String ERROR_EMAIL_INVALID = "L'adresse email n'est pas valide";
public static final String ERROR_PASSWORD_TOO_SHORT = "Le mot de passe doit contenir au moins 8 caractères";
public static final String ERROR_FILE_TOO_LARGE = "Le fichier est trop volumineux (max 10MB)";
public static final String ERROR_FILE_TYPE_NOT_ALLOWED = "Type de fichier non autorisé";

// Messages métier
public static final String MSG_DOCUMENT_UPLOADED = "Document déposé avec succès";
public static final String MSG_DOCUMENT_VALIDATED = "Document validé";
public static final String MSG_DOCUMENT_REJECTED = "Document rejeté";
public static final String MSG_MESSAGE_SENT = "Message envoyé";
```

---

## 🔐 Configuration Sécurité

### **Configuration JWT**

```java
// Propriétés JWT
@Value("${jwt.secret}")
private String jwtSecret;

@Value("${jwt.expiration}")
private long jwtExpiration;

@Value("${jwt.refresh-expiration}")
private long refreshExpiration;

// Configuration de sécurité
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
```

### **Règles de Sécurité**

```java
// Endpoints publics (sans authentification)
public static final String[] PUBLIC_ENDPOINTS = {
    "/api/auth/login",
    "/api/auth/register",
    "/api/auth/refresh",
    "/api/auth/validate"
};

// Endpoints pour clients
public static final String[] CLIENT_ENDPOINTS = {
    "/api/documents/upload",
    "/api/documents/user/**",
    "/api/messages/**",
    "/api/notifications/**"
};

// Endpoints pour comptables
public static final String[] COMPTABLE_ENDPOINTS = {
    "/api/documents/validate/**",
    "/api/documents/reject/**",
    "/api/documents/non-traites",
    "/api/admin/statistics"
};

// Endpoints pour administrateurs
public static final String[] ADMIN_ENDPOINTS = {
    "/api/users/**",
    "/api/admin/**"
};
```

---

## 🗄️ Configuration Base de Données

### **Configuration PostgreSQL**

```yaml
# Configuration de la base de données
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:portail_cabinet}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:password}
    driver-class-name: org.postgresql.Driver
    
    # Configuration du pool de connexions
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

### **Configuration JPA/Hibernate**

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update  # create, create-drop, update, validate, none
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        use_sql_comments: true
        
        # Configuration du cache
        cache:
          use_second_level_cache: true
          use_query_cache: true
          region.factory_class: org.hibernate.cache.ehcache.EhCacheRegionFactory
```

### **Scripts de Base de Données**

```sql
-- Création de la base de données
CREATE DATABASE portail_cabinet;

-- Création des tables (générées automatiquement par JPA)
-- Les tables seront créées automatiquement grâce à ddl-auto: update

-- Index recommandés
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_documents_user ON documents(expediteur_id, destinataire_id);
CREATE INDEX idx_documents_statut ON documents(statut);
CREATE INDEX idx_messages_conversation ON messages(expediteur_id, destinataire_id);
CREATE INDEX idx_notifications_destinataire ON notifications(destinataire_id);
```

---

## 🔧 Configuration de Développement

### **Profil de Développement (`application-dev.yml`)**

```yaml
# Configuration pour le développement
spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create-drop  # Recréer les tables à chaque démarrage
      
logging:
  level:
    com.cabinet.portail: DEBUG
    org.springframework.security: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE

# Configuration pour les tests
app:
  test-mode: true
  mock-email: true
```

### **Profil de Production (`application-prod.yml`)**

```yaml
# Configuration pour la production
spring:
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: validate  # Valider le schéma sans modification
      
logging:
  level:
    com.cabinet.portail: INFO
    org.springframework.security: WARN
    root: WARN

# Configuration de sécurité renforcée
app:
  test-mode: false
  mock-email: false
  security:
    require-https: true
    max-login-attempts: 3
```

---

## 📝 Notes de Configuration

### **Bonnes Pratiques**

✅ **Sécurité :**
- Utiliser des secrets forts pour JWT
- Configurer HTTPS en production
- Limiter les tentatives de connexion
- Valider tous les inputs

✅ **Performance :**
- Configurer le pool de connexions
- Utiliser les index appropriés
- Activer le cache Hibernate
- Optimiser les requêtes

✅ **Maintenance :**
- Externaliser les configurations
- Utiliser les profils Spring
- Documenter les variables
- Versionner les configurations

---

*Configuration générée pour le projet Portail Cabinet Comptable - Version 1.0* 