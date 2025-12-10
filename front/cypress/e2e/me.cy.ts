/// <reference types="cypress" />

describe('Profil utilisateur', () => {
  it("Affichage des informations de l'utilisateur", () => {
    cy.intercept('GET', '/api/user/1', {
      statusCode: 200,
      body: { email: 'test@user.com', firstName: 'Test', lastName: 'User' }
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
    cy.visit('http://localhost:4200/me');
    cy.contains('test@user.com').should('exist');
    cy.contains('Test').should('exist');
    cy.contains('User').should('exist');
  });
});
