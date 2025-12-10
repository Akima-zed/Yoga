/// <reference types="cypress" />

describe('Session Create', () => {
  it('Création de session', () => {
    cy.intercept('POST', '/api/session', { statusCode: 201 });
    cy.intercept('GET', '/api/teacher', {
      statusCode: 200,
      body: [
        { id: 1, firstName: 'Prof', lastName: 'Yoga', email: 'prof@yoga.com' }
      ]
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
    cy.visit('/sessions/create');
    cy.get('form').should('be.visible');
    cy.get('input[formControlName=name]').type('Yoga Midi');
    cy.get('input[formControlName=date]').type('2025-12-12');
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('mat-option').contains('Prof Yoga').click();
    cy.get('textarea[formControlName=description]').type('Séance de yoga midi.');
    cy.get('button[type=submit]').click();
    cy.url().should('include', '/sessions');
  });

  it('Erreur si champ obligatoire manquant', () => {
    cy.intercept('GET', '/api/teacher', {
      statusCode: 200,
      body: [
        { id: 1, firstName: 'Prof', lastName: 'Yoga', email: 'prof@yoga.com' }
      ]
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
    cy.visit('/sessions/create');
    cy.get('input[formControlName=date]').type('2025-12-12');
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('mat-option').contains('Prof Yoga').click();
    cy.get('button[type=submit]').should('be.disabled');
  });
});
