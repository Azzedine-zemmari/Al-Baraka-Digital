# 🏦 Al Baraka Digital – Plateforme Bancaire Sécurisée

## 📌 Contexte
**Al Baraka Digital** est une plateforme bancaire sécurisée visant à digitaliser la gestion des opérations bancaires pour les clients et les agents internes de la banque.

Les opérations couvertes incluent :
- Dépôts
- Retraits
- Virements

L’objectif principal est d’assurer des opérations **sécurisées**, **traçables** et **conformes aux règles internes**, notamment pour les montants importants nécessitant une validation humaine.

---

## ❗ Problèmes identifiés
- Traitement manuel des opérations sensibles
- Risque élevé de fraude ou d’erreurs humaines
- Manque de traçabilité et de contrôle interne
- Absence d’automatisation sécurisée pour les comptes clients et agents

---

## 🎯 Objectifs du projet
- Sécuriser l’accès aux APIs via **JWT (stateless)**
- Implémenter la logique métier des dépôts, retraits et virements
- Mettre en place des **workflows de validation** selon le montant
- Sécuriser certains endpoints sensibles via **OAuth2**
- Conteneuriser l’application avec **Docker**
- Préparer une base solide pour une future intégration **CI/CD**

---

## 👥 Rôles du système

| Rôle | Actions principales |
|-----|------------------|
| **CLIENT** | Créer un compte, se connecter, créer des opérations, uploader des justificatifs |
| **AGENT_BANCAIRE** | Consulter les opérations PENDING, approuver ou rejeter les opérations |
| **ADMIN** | Créer et gérer les comptes clients et admins, gérer le statut des comptes |

---

## 🧩 Scénarios métier

### 1️⃣ Création de compte (Client)
**Prérequis** : le client ne possède pas de compte

**Action** :
- Remplir le formulaire (email, mot de passe, nom complet)

**Résultat attendu** :
- Compte client créé
- Numéro de compte unique généré automatiquement
- Le client peut se connecter

---

### 2️⃣ Authentification (Login)
**Action** :
- Saisie de l’email et du mot de passe

**Résultat attendu** :
- Authentification réussie
- Génération d’un **JWT** pour l’accès aux APIs

---

### 3️⃣ Dépôt (Deposit)
**Prérequis** : Client connecté, compte actif

**Cas A – Montant ≤ 10 000 DH**
- Validation automatique
- Solde augmenté immédiatement

**Cas B – Montant > 10 000 DH**
- Upload d’un justificatif (PDF/JPG/PNG – max 5MB)
- Statut **PENDING**
- Validation ou rejet par un agent bancaire

---

### 4️⃣ Retrait (Withdrawal)
**Prérequis** : Client connecté, solde suffisant

**Cas A – Montant ≤ 10 000 DH**
- Validation automatique
- Solde diminué

**Cas B – Montant > 10 000 DH**
- Justificatif requis
- Opération en **PENDING**
- Solde mis à jour uniquement après approbation

---

### 5️⃣ Virement (Transfer)
**Prérequis** : Solde du compte source suffisant

**Cas A – Montant ≤ 10 000 DH**
- Validation automatique
- Mise à jour des deux soldes

**Cas B – Montant > 10 000 DH**
- Justificatif requis
- Validation par un agent
- Mise à jour du solde uniquement si approuvée

---

### 6️⃣ Gestion par l’agent bancaire
**Prérequis** :
- Agent connecté
- Autorisation OAuth2 valide

**Actions** :
- Consulter les opérations **PENDING**
- Vérifier les justificatifs
- Approuver ou rejeter les opérations

**Sécurité** :
- Lecture des opérations PENDING protégée par **OAuth2 (scope: operations.read)**
- Approbation / rejet protégés par **JWT**

---

### 7️⃣ Gestion des comptes (Admin)
**Actions** :
- Créer, modifier et supprimer des comptes clients ou admins
- Activer / désactiver des comptes

---

## 🔐 Sécurité
- Authentification **JWT stateless**
- **Spring Security 6**
- `UserDetailsService` personnalisé
- `PasswordEncoder` : **BCrypt**
- OAuth2 Resource Server (Keycloak / Okta)

### Sécurisation des endpoints

| Endpoint | Rôle | Sécurité |
|--------|------|---------|
| `/api/client/**` | CLIENT | JWT |
| `/api/agent/**` | AGENT | JWT |
| `/api/agent/operations/pending` | AGENT | OAuth2 (scope: operations.read) |
| `/api/admin/**` | ADMIN | JWT |

---

## 🌐 Endpoints principaux

| Endpoint | Méthode | Rôle | Description |
|--------|--------|------|------------|
| `/auth/login` | POST | Tous | Authentification + JWT |
| `/api/client/operations` | POST | CLIENT | Créer une opération |
| `/api/client/operations/{id}/document` | POST | CLIENT | Upload justificatif |
| `/api/client/operations` | GET | CLIENT | Lister ses opérations |
| `/api/agent/operations/pending` | GET | AGENT | Lister opérations PENDING |
| `/api/agent/operations/{id}/approve` | PUT | AGENT | Approuver une opération |
| `/api/agent/operations/{id}/reject` | PUT | AGENT | Rejeter une opération |
| `/api/admin/users` | POST/PUT/DELETE | ADMIN | Gestion des comptes |

---

## 🗄️ Modèle de données

### User
- id
- email
- password
- fullName
- role
- active
- createdAt

### Account
- id
- accountNumber
- balance
- owner

### Operation
- id
- type
- amount
- status
- createdAt
- validatedAt
- executedAt
- accountSource
- accountDestination

### Document
- id
- fileName
- fileType
- storagePath
- uploadedAt
- operation

---

## 🐳 Docker
- Dockerfile pour le backend
- Variables d’environnement :
  - `JWT_SECRET`
  - `DB_URL`
  - `DB_USER`
  - `DB_PASSWORD`

Optionnel : **Docker Compose** pour lancer l’application + base de données

---

## 🚀 Déploiement
- Application conteneurisée
- Déploiement isolé et reproductible
- Prête pour intégration CI/CD

---

## 📚 Technologies utilisées
- Java 17
- Spring Boot
- Spring Security 6
- JWT
- OAuth2 (Keycloak)
- JPA / Hibernate
- Docker
- Github Actions

---

## 👨‍💻 Auteur
**Azzedine Zemmari**  
Développeur Java / Spring Boot / Sécurité

