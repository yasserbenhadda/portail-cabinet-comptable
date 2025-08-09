# 🧪 Guide de Test - Frontend Angular

## 🚀 **Démarrage Rapide**

### **1. Vérifier que le backend est démarré**
```bash
# Backend Spring Boot doit être sur le port 8085
curl http://localhost:8085/api/auth/login
```

### **2. Démarrer le frontend Angular**
```bash
cd portail-frontend
ng serve --port 4200 --open
```

### **3. Accéder à l'application**
- **URL** : `http://localhost:4200`
- **Page par défaut** : Page de connexion

---

## 📋 **Tests d'Authentification**

### **🔐 Test 1 : Page de Connexion**

#### **Étapes :**
1. **Accéder à** : `http://localhost:4200/login`
2. **Vérifier l'interface** :
   - ✅ Formulaire avec champs email/password
   - ✅ Bouton "Se connecter"
   - ✅ Lien vers inscription
   - ✅ Design Material Design moderne

#### **Test de validation :**
- **Email vide** → Message d'erreur
- **Email invalide** → Message d'erreur
- **Mot de passe vide** → Message d'erreur
- **Mot de passe < 6 caractères** → Message d'erreur

#### **Test de connexion :**
```json
{
  "email": "dupont@example.com",
  "password": "password123"
}
```

**Résultat attendu :**
- ✅ Redirection vers `/dashboard`
- ✅ Affichage des informations utilisateur
- ✅ Token stocké dans localStorage

---

### **📝 Test 2 : Page d'Inscription**

#### **Étapes :**
1. **Accéder à** : `http://localhost:4200/register`
2. **Vérifier l'interface** :
   - ✅ Formulaire complet (nom, email, rôle, password, confirm)
   - ✅ Sélecteur de rôle (Client/Admin)
   - ✅ Validation des mots de passe
   - ✅ Bouton "S'inscrire"

#### **Test de validation :**
- **Nom < 2 caractères** → Message d'erreur
- **Email invalide** → Message d'erreur
- **Mots de passe différents** → Message d'erreur
- **Champs vides** → Messages d'erreur

#### **Test d'inscription :**
```json
{
  "nom": "Nouveau Client",
  "email": "nouveau@example.com",
  "password": "password123",
  "role": "CLIENT"
}
```

**Résultat attendu :**
- ✅ Redirection vers `/dashboard`
- ✅ Compte créé en base de données
- ✅ Token généré automatiquement

---

### **🏠 Test 3 : Dashboard**

#### **Étapes :**
1. **Se connecter** avec un compte valide
2. **Accéder à** : `http://localhost:4200/dashboard`
3. **Vérifier l'interface** :
   - ✅ Toolbar avec nom utilisateur
   - ✅ Bouton de déconnexion
   - ✅ Carte d'informations utilisateur
   - ✅ Design responsive

#### **Test de déconnexion :**
1. **Cliquer sur** le bouton de déconnexion
2. **Vérifier** :
   - ✅ Redirection vers `/login`
   - ✅ Token supprimé du localStorage
   - ✅ État utilisateur réinitialisé

---

## 🔧 **Tests Techniques**

### **🔍 Test 4 : Gestion des Erreurs**

#### **Test erreur réseau :**
1. **Arrêter le backend** Spring Boot
2. **Tenter une connexion** sur le frontend
3. **Vérifier** :
   - ✅ Message d'erreur approprié
   - ✅ Interface non bloquée
   - ✅ Possibilité de réessayer

#### **Test erreur 401 :**
1. **Utiliser des identifiants incorrects**
2. **Vérifier** :
   - ✅ Message "Email ou mot de passe incorrect"
   - ✅ Formulaire réinitialisé
   - ✅ Possibilité de réessayer

#### **Test erreur 409 (Register) :**
1. **Créer un compte** avec un email existant
2. **Vérifier** :
   - ✅ Message "Cet email est déjà utilisé"
   - ✅ Formulaire conservé
   - ✅ Possibilité de modifier

---

### **📱 Test 5 : Responsive Design**

#### **Test mobile (< 480px) :**
1. **Ouvrir les outils de développement**
2. **Simuler un appareil mobile**
3. **Vérifier** :
   - ✅ Adaptation des formulaires
   - ✅ Boutons accessibles
   - ✅ Texte lisible
   - ✅ Navigation fluide

#### **Test tablette (480px - 768px) :**
1. **Ajuster la largeur de fenêtre**
2. **Vérifier** :
   - ✅ Adaptation des cartes
   - ✅ Espacement approprié
   - ✅ Navigation optimisée

---

## 🛠️ **Tests de Performance**

### **⚡ Test 6 : Temps de Chargement**

#### **Mesures :**
- **Premier chargement** : < 3 secondes
- **Navigation entre pages** : < 1 seconde
- **Soumission de formulaires** : < 2 secondes

#### **Outils de mesure :**
- **Chrome DevTools** → Network tab
- **Lighthouse** → Performance audit
- **Angular DevTools** → Component inspection

---

## 🔒 **Tests de Sécurité**

### **🛡️ Test 7 : Protection des Routes**

#### **Test accès non autorisé :**
1. **Se déconnecter**
2. **Essayer d'accéder à** `/dashboard`
3. **Vérifier** :
   - ✅ Redirection vers `/login`
   - ✅ Pas d'accès aux données utilisateur

#### **Test manipulation localStorage :**
1. **Supprimer le token** manuellement
2. **Rafraîchir la page**
3. **Vérifier** :
   - ✅ Redirection vers login
   - ✅ État cohérent

---

## 📊 **Tests de Compatibilité**

### **🌐 Test 8 : Navigateurs**

#### **Navigateurs à tester :**
- ✅ **Chrome** (recommandé)
- ✅ **Firefox**
- ✅ **Safari**
- ✅ **Edge**

#### **Points de vérification :**
- ✅ Rendu des composants Material
- ✅ Fonctionnement des formulaires
- ✅ Gestion des erreurs
- ✅ Responsive design

---

## 🚨 **Scénarios d'Erreur**

### **❌ Cas d'erreur à tester :**

#### **1. Backend indisponible :**
- ✅ Message d'erreur clair
- ✅ Interface non bloquée
- ✅ Possibilité de réessayer

#### **2. Token expiré :**
- ✅ Déconnexion automatique
- ✅ Redirection vers login
- ✅ Message informatif

#### **3. Données corrompues :**
- ✅ Gestion gracieuse des erreurs
- ✅ Fallback des valeurs
- ✅ Logs de debug

---

## 📝 **Checklist de Validation**

### **✅ Interface Utilisateur**
- [ ] Design moderne et professionnel
- [ ] Responsive sur tous les écrans
- [ ] Animations fluides
- [ ] Feedback utilisateur clair
- [ ] Accessibilité (contraste, navigation)

### **✅ Fonctionnalités**
- [ ] Connexion fonctionnelle
- [ ] Inscription fonctionnelle
- [ ] Déconnexion fonctionnelle
- [ ] Gestion des erreurs
- [ ] Validation des formulaires

### **✅ Performance**
- [ ] Temps de chargement acceptable
- [ ] Pas de fuites mémoire
- [ ] Optimisation des bundles
- [ ] Cache efficace

### **✅ Sécurité**
- [ ] Protection des routes
- [ ] Gestion sécurisée des tokens
- [ ] Validation côté client
- [ ] Pas de données sensibles exposées

---

## 🎯 **Résultats Attendus**

### **🎉 Succès :**
- ✅ Application accessible sur `http://localhost:4200`
- ✅ Authentification complète fonctionnelle
- ✅ Interface utilisateur moderne et responsive
- ✅ Intégration backend réussie
- ✅ Gestion d'erreurs robuste

### **📈 Métriques de Qualité :**
- **Performance** : Score Lighthouse > 90
- **Accessibilité** : Score Lighthouse > 95
- **SEO** : Score Lighthouse > 90
- **Best Practices** : Score Lighthouse > 95

---

## 🚀 **Prochaines Étapes**

### **Phase 2 - Gestion des Documents**
- [ ] Composant d'upload de documents
- [ ] Liste et consultation des documents
- [ ] Partage et permissions
- [ ] Recherche et filtres

### **Phase 3 - Messagerie**
- [ ] Interface de messagerie
- [ ] Notifications en temps réel
- [ ] Historique des conversations

### **Phase 4 - IA ChatBot**
- [ ] Interface de chat
- [ ] Intégration IA
- [ ] Analyse de documents

---

**🎯 Objectif : Interface utilisateur moderne et intuitive pour le portail cabinet comptable !** 