/// <reference types="cypress" />

describe('Login', () => {
  it('Connexion réussie', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: {
        id: 1,
        email: 'user@exemple.com',
        firstName: 'Test',
        lastName: 'User',
        admin: true
      }
    });
    cy.visit('/login');
    cy.get('input[formControlName=email]').type('user@exemple.com');
    cy.get('input[formControlName=password]').type('motdepasse');
    cy.get('button[type=submit]').click();
    cy.url().should('include', '/sessions');
  });

  it('Erreur de connexion', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: { message: 'Identifiants invalides' }
    });
    cy.visit('/login');
    cy.get('input[formControlName=email]').type('user@exemple.com');
    cy.get('input[formControlName=password]').type('mauvaismotdepasse');
    cy.get('button[type=submit]').click();
    cy.contains('Identifiants invalides').should('be.visible');
  });
});
