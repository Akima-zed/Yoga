/// <reference types="cypress" />

describe('Sessions', () => {
  beforeEach(() => {
    cy.intercept('GET', '/api/session', [
      { id: 1, name: 'Yoga Matin', teacher: 'Alice', date: '2025-12-10' },
      { id: 2, name: 'Yoga Soir', teacher: 'Bob', date: '2025-12-11' }
    ]);
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

  it('Affichage de la liste des sessions', () => {
    cy.visit('http://localhost:4200/sessions');
    cy.contains('Yoga Matin').should('be.visible');
    cy.contains('Yoga Soir').should('be.visible');
  });

  it('Boutons Create et Detail visibles pour admin', () => {
    cy.visit('http://localhost:4200/sessions');
    cy.contains('Create').should('be.visible');
    cy.get('button').contains('Detail').should('exist');
  });
});
