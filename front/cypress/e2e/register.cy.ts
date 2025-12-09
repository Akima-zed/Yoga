describe('Register spec', () => {
  beforeEach(() => {
    cy.clearLocalStorage();
    cy.intercept('GET', '/api/session', { statusCode: 200, body: null });
    cy.reload();
  });
  it('Register success', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 201,
      body: {
        id: 2,
        username: 'newUser',
        firstName: 'New',
        lastName: 'User',
        admin: false
      },
    });
    // Ne pas ajouter de session avant la visite de /register
    cy.clearLocalStorage();
    cy.visit('/register');
    cy.intercept('GET', '/api/session', { statusCode: 200, body: null });
    cy.get('input[formControlName=firstName]', { timeout: 10000 }).should('exist').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=email]').type('new@user.com');
    cy.get('input[formControlName=password]').type('password123');
    cy.get('button[type=submit]').should('not.be.disabled').click();
    cy.url().should('include', '/login');
  });

  it('Register error - missing email', () => {
    cy.window().then(win => {
      win.localStorage.setItem('sessionInformation', JSON.stringify({
        id: 2,
        admin: false,
        email: '',
        firstName: 'New',
        lastName: 'User'
      }));
    });
    cy.clearLocalStorage();
    cy.visit('/register');
    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    // Ne pas remplir l'email
    cy.get('input[formControlName=password]').type('password123');
    cy.get('button[type=submit]').should('be.disabled');
    cy.get('input[formControlName=email]').focus().blur();
  });

  it('Register error - missing password', () => {
    cy.visit('/register');
    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=email]').type('new@user.com');
    // Ne pas remplir le mot de passe
    cy.get('button[type=submit]').should('be.disabled');
  });
});