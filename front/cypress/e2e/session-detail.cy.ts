describe('Session Detail spec', () => {
    beforeEach(() => {
      cy.clearLocalStorage();
      cy.intercept('GET', '/api/session/1', {
        id: 1,
        name: 'Yoga Matin',
        teacher_id: 1,
        date: '2025-12-10',
        users: [1, 2],
        description: 'Séance test.'
      });
      cy.intercept('GET', '/api/teacher/1', {
        id: 1,
        firstName: 'Alice',
        lastName: '',
        email: 'alice@yoga.com'
      });
      cy.intercept('GET', '/api/session', [
        { id: 1, name: 'Yoga Matin', teacher_id: 1, date: '2025-12-10', users: [1, 2], description: 'Séance test.' }
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
  it('Affichage des informations de la session', () => {
    cy.visit('/sessions/detail/1');
    cy.wait(100);
    cy.contains('Yoga Matin').should('be.visible');
    cy.contains('Alice').should('be.visible');
    cy.contains('December 10, 2025').should('be.visible');
  });

  it('Bouton Delete visible pour admin', () => {
    cy.visit('/sessions/detail/1');
    cy.get('button').contains('Delete').should('exist');
  });
});