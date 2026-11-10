# Douane Temporelle
**Mini-projet de Programmation Mobile — 3BI FSEGT**

**Enseignant :** M. Nidhal Jelassi  
**Technologies :** Java, XML, Android Studio, GitHub

## Présentation
Douane Temporelle est une application Android qui permet d'enregistrer un voyageur temporel, de détecter les risques de paradoxe et d'afficher un verdict.

## Membres du groupe

### 1. Laila Ben Mahrez — 3BI A05
**Activité 1 : MainActivity**
- Création du formulaire d'enregistrement en XML.
- Saisie du nom, des années et du motif du voyage.
- Validation et transmission des données à l'Activité 2 par Intent.

**Branche :** `laila-activite1`

### 2. Youssef Bedhiafi — 3BI A04
**Activité 2 : VerificationActivity**
- Création du passeport temporel en XML.
- Affichage des informations et calcul de l'âge temporel.
- Détection des nombres premiers et des paradoxes.
- Programmation des boutons Approuver et Refuser.
- Création de `IntentKeyHelper.java` pour les clés des Intents.

**Branche :** `youssef-activite2`

### 3. Om Elkhir Ghofrane Mahmoud — 3BI A02
**Activité 3 : ResultActivity**
- Réception du verdict.
- Affichage d'un fond vert (approbation) ou rouge (refus).
- Affichage d'un message personnalisé.
- Bouton Nouveau voyageur pour revenir au formulaire.

**Branche :** `ghofrane-activite3`

## Fonctionnement
`MainActivity` → `VerificationActivity` → `ResultActivity`

L'approbation est désactivée si le motif est **Modification du passé** et si la différence entre l'année de destination et l'année de naissance est un **nombre premier**.

## Collaboration GitHub
Chaque étudiant développe son activité sur sa propre branche, puis propose ses modifications avec une Pull Request vers `main`.

## Exécution
1. Ouvrir le projet dans Android Studio.
2. Synchroniser Gradle.
3. Compiler avec **Build > Assemble Project**.
4. Lancer l'application sur un émulateur Android.

---
**Projet réalisé par Youssef Bedhiafi, Laila Ben Mahrez et Om Elkhir Ghofrane Mahmoud — 2026/2027.**
