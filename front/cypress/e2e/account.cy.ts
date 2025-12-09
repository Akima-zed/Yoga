describe('Account spec', () => {
  it("Affichage des informations de l'utilisateur", () => {
    cy.intercept('GET', /\/api\/user\/.*/, {
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
    cy.visit('/me');
    cy.contains('test@user.com').should('exist');
  });
});