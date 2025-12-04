# Documentation — UserService

## Emplacement
`back/src/main/java/com/openclassrooms/starterjwt/services/UserService.java`

## But
Service Spring chargé des opérations CRUD (lecture, création, suppression) pour les utilisateurs, avec validation métier et levée d'exceptions personnalisées.

## Dépendances principales
- `UserRepository` — accès aux données.
- Exceptions personnalisées : `NotFoundException`, `BadRequestException`.

## Comportement général
- Recherche d'utilisateur par `id` ou `email` — si absent, lance `NotFoundException`.
- Création d'utilisateur — vérifie l'unicité de l'email et lance `BadRequestException` si l'email existe déjà.
- Suppression d'utilisateur — supprime l'entité trouvée.

## Méthodes (signature + description)

- `public @NonNull User findById(@NonNull Long id)`
  - Retourne l'utilisateur correspondant à l'`id` fourni.
  - Lance `NotFoundException` si aucun utilisateur n'est trouvé.

- `public @NonNull User findByEmail(@NonNull String email)`
  - Retourne l'utilisateur correspondant à l'`email` fourni.
  - Lance `NotFoundException` si aucun utilisateur n'est trouvé.

- `public boolean existsByEmail(@NonNull String email)`
  - Retourne `true` si un utilisateur avec cet email existe, sinon `false`.
  - Utilisé principalement comme check avant création.

- `public @NonNull User create(@NonNull String email, @NonNull String lastName, @NonNull String firstName, @NonNull String password, boolean isAdmin)`
  - Crée et persiste un nouvel utilisateur.
  - Vérifie d'abord `existsByEmail(email)` ; si vrai, lance `BadRequestException` avec le message "Email déjà utilisé".
  - Construit un `User` via le `builder()` et le sauvegarde avec `userRepository.save(user)`.
  - Retourne l'entité persistée.

- `public void delete(@NonNull Long id)`
  - Supprime l'utilisateur identifié par `id`.
  - Appelle `findById(id)` (donc propage `NotFoundException` si introuvable), puis `userRepository.delete(...)`.

## Exceptions levées
- `NotFoundException` — utilisé quand un utilisateur recherché n'existe pas.
- `BadRequestException` — utilisé pour signaler des erreurs de validation (par ex. email déjà pris).

## Exemple d'utilisation (snippet Java)

```java
// Récupération
User user = userService.findById(42L);

// Création
User newUser = userService.create("alice@example.com", "Dupont", "Alice", "secret", false);

// Vérification existence
boolean exists = userService.existsByEmail("alice@example.com");

// Suppression
userService.delete(newUser.getId());
```

## Remarques & bonnes pratiques
- Les mots de passe doivent être encodés avant d'être passés à `create(...)`. La classe actuelle stocke le `password` tel quel — envisager d'ajouter un `PasswordEncoder` (ex. `BCryptPasswordEncoder`) et d'encoder avant la sauvegarde.
- Les messages d'exception sont en clair : pour la production, assurer qu'aucune information sensible ne fuit via ces messages.
- Pour tester : ajouter des tests unitaires pour les chemins heureux et les chemins d'erreur (email déjà utilisé, utilisateur introuvable).

## Étapes suivantes (suggestions)
- Ajouter un `UserServiceTest` couvrant : création réussie, tentative de création avec email existant, recherche introuvable, suppression.
- Transformer la documentation en Javadoc directement dans `UserService.java` si vous préférez la documentation inline.
- Fournir une version anglaise de cette documentation si nécessaire.

---
Fichier généré automatiquement pour faciliter la relecture de la classe `UserService`.
