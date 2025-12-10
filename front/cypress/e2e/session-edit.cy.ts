/// <reference types="cypress" />

describe('Session Edit', () => {
  beforeEach(() => {
    cy.intercept('GET', '/api/session/1', {
      id: 1,
      name: 'Yoga Matin',
      teacher_id: 1,
      date: '2025-12-10',
      users: [1],
      description: 'Séance test.'
    });
    cy.intercept('GET', '/api/teacher', {
      statusCode: 200,
      body: [
        { id: 1, firstName: 'Prof', lastName: 'Yoga', email: 'prof@yoga.com' }
      ]
    });
    cy.intercept('GET', '/api/teacher/1', {
      id: 1,
      firstName: 'Prof',
      lastName: 'Yoga',
      email: 'prof@yoga.com'
    });
    cy.window().then(win => {
      win.localStorage.setItem('sessionInformation', JSON.stringify({
        id: 1,
        admin: true,
        email: 'test@user.com',
        firstName: 'Test',
        lastName: 'User'
      }));
    });
  });

  it('Modification de session', () => {
    cy.intercept('PUT', '/api/session/1', { statusCode: 200 });
    cy.visit('http://localhost:4200/sessions/update/1');
    cy.get('input[formControlName=name]').clear().type('Yoga Avancé');
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('mat-option').contains('Prof Yoga').click();
    cy.get('textarea[formControlName=description]').type('Séance avancée.');
    cy.get('button[type=submit]').click();
    cy.url().should('include', '/sessions');
  });

  it('Erreur si champ obligatoire manquant', () => {
    cy.visit('http://localhost:4200/sessions/update/1');
    cy.get('input[formControlName=name]').clear();
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('mat-option').contains('Prof Yoga').click();
    cy.get('textarea[formControlName=description]').type('Séance avancée.');
    cy.get('button[type=submit]').should('be.disabled');
  });
});
