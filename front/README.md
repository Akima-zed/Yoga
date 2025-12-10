# Yoga

## Prérequis

- Node.js (version recommandée : >=18)
- Angular CLI (version 19)
- Avoir implémenté les tests unitaires, d’intégration et E2E

## Installation et lancement de l’application

1. Cloner le projet :
	```
	git clone https://github.com/OpenClassrooms-Student-Center/P5-Full-Stack-testing
	```
2. Aller dans le dossier du frontend :
	```
	cd yoga/front
	```
3. Installer les dépendances :
	```
	npm install
	```
4. Lancer l’application :
	```
	npm run start
	```

L’application sera accessible sur [http://localhost:4200](http://localhost:4200)

## Lancer et exécuter les différents tests

### Tests unitaires (Jest)

Pour exécuter les tests unitaires :
```
npm run test
```
Pour exécuter les tests en mode watch :
```
npm run test:watch
```

### Tests E2E (Cypress)

Pour exécuter les tests E2E :
```
npm run e2e
```

## Générer les rapports de couverture

### Rapport de couverture des tests unitaires

Après avoir lancé les tests unitaires, le rapport de couverture est généré automatiquement :
- `front/coverage/lcov-report/index.html`

### Rapport de couverture des tests E2E

Après avoir lancé les tests E2E, générez le rapport de couverture :
```
npm run e2e:coverage
```
Le rapport est disponible ici :
- `front/cypress/coverage/index.html`

## Seuil de couverture

Le projet garantit un seuil de couverture d’au moins **80%** pour chaque indicateur (instructions, branches, etc.) sur le frontend. Vérifiez les rapports pour chaque métrique.

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 19.2.16.

## Start the project

Git clone:

> git clone https://github.com/OpenClassrooms-Student-Center/P5-Full-Stack-testing

Go inside folder:

> cd yoga

Install dependencies:

> npm install

Launch Front-end:

> npm run start;

## Project structure

This repository is split into two distinct parts:

- **front/**: Angular frontend (this folder)
- **back/**: Java Spring Boot backend (see `back/README.md` for backend instructions)

## Test coverage

The frontend includes both unit and E2E tests. Coverage reports are generated for both:

- **Unit tests**: Run with Jest
- **E2E tests**: Run with Cypress

To check coverage:

```
npm run test
npm run e2e
npm run e2e:coverage
```

Coverage reports are available in:
- `front/coverage/lcov-report/index.html` (unit)
- `front/cypress/coverage/index.html` (E2E)

## Technologies used

- **Angular 19**
- **Jest** (unit tests)
- **Cypress** (E2E tests)
- **Node.js**

## Contribution

To contribute:
1. Fork the repository
2. Create a feature branch
3. Add your tests and features
4. Check coverage (aim for >80%)
5. Submit a Pull Request

## Separation from backend

The backend is completely separated and located in the `back/` folder. See `back/README.md` for backend setup and instructions.


### Test

#### E2E

Launching e2e test:

> npm run e2e

Generate coverage report (you should launch e2e test before):

> npm run e2e:coverage

Report is available here:

> front/coverage/lcov-report/index.html

#### Unitary test

Launching test:

> npm run test

for following change:

> npm run test:watch
