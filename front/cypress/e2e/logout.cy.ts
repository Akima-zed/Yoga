describe('Logout spec', () => {
  it('Déconnexion utilisateur', () => {
    cy.window().then(win => {
      win.localStorage.setItem('sessionInformation', JSON.stringify({
        id: 1,
        admin: true,
        email: 'test@user.com',
        firstName: 'Test',
        lastName: 'User'
      }));
    });
    cy.visit('/sessions');
    cy.contains('span', /logout/i).should('exist').click();
    cy.url().should('include', '/login');
  });
});