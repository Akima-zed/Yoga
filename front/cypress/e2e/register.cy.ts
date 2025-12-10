/// <reference types="cypress" />

describe('Register', () => {
  it('Création de compte réussie', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 201,
      body: {
        id: 2,
        email: 'new@user.com',
        firstName: 'New',
        lastName: 'User',
        admin: false
      }
    });
    cy.visit('http://localhost:4200/register');
    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=email]').type('new@user.com');
    cy.get('input[formControlName=password]').type('password123');
    cy.get('button[type=submit]').click();
    cy.url().should('include', '/login');
  });

  it('Erreur si email manquant', () => {
    cy.visit('http://localhost:4200/register');
    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=password]').type('password123');
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('Erreur si mot de passe manquant', () => {
    cy.visit('http://localhost:4200/register');
    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=email]').type('new@user.com');
    cy.get('button[type=submit]').should('be.disabled');
  });
});
