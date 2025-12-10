/// <reference types="cypress" />

describe('Session Delete', () => {
  beforeEach(() => {
    cy.intercept('GET', '/api/session/1', {
      id: 1,
      name: 'Yoga Matin',
      teacher_id: 1,
      date: '2025-12-10',
      users: [1],
      description: 'Séance test.'
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

  it('Suppression de session', () => {
    cy.intercept('DELETE', '/api/session/1', { statusCode: 200 });
    cy.visit('http://localhost:4200/sessions/detail/1');
    cy.get('button').contains('Delete').should('exist').click();
    cy.url().should('include', '/sessions');
  });
});
