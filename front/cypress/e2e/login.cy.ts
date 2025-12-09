describe('Login spec', () => {
    beforeEach(() => {
      cy.clearLocalStorage();
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true
        },
      });
      cy.intercept('GET', '/api/session', [
        { id: 1, name: 'Yoga Matin', teacher: 'Alice', date: '2025-12-10' }
      ]);
    });
  it('Login successfull', () => {
    cy.visit('/login');
    cy.get('input[formControlName=email]').type("yoga@studio.com");
    cy.get('input[formControlName=password]').type("test!1234");
    cy.get('button[type=submit]').click();
    cy.url().should('include', '/sessions');
  })
});