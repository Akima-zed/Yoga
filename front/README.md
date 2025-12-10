# Yoga

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
