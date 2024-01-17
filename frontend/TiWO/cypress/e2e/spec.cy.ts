describe('template spec', () => {
  it('passes', () => {
    cy.visit('http://localhost:4200/')
  })

  it('passes', () => {
    cy.visit('http://localhost:4200/')
    cy.get('input[id = emailRegister]').type('test@test.pl')
    cy.get('input[id = usernameRegister]').type('test')
    cy.get('input[id = passwordRegister]').type('test')
    cy.get('input[id = registerButton]').click()
  })

  it('passes', () => {
    cy.visit('http://localhost:4200/')
    cy.get('input[id = usernameLogin]').type('admin')
    cy.get('input[id = passwordLogin]').type('admin')
    cy.get('input[id = loginButton]').click()
  })

  it('passes', () => {
    cy.visit('http://localhost:4200/')
    cy.get('input[id = usernameLogin]').type('admin')
    cy.get('input[id = passwordLogin]').type('admin')
    cy.get('input[id = loginButton]').click()
    cy.get('button[id = logoutButton]').click()
  })

  it('passes', () => {
    cy.visit('http://localhost:4200/')
    cy.get('input[id = usernameLogin]').type('admin')
    cy.get('input[id = passwordLogin]').type('admin')
    cy.get('input[id = loginButton]').click()
    cy.get('button[id = addListButton]').click()
    cy.get('input[id = name]').type('nowalista')
    cy.get('input[id = date]').type('2023-01-27')
    cy.get('input[id = addButton]').click()
  })

  it('passes', () => {
    cy.visit('http://localhost:4200/')
    cy.get('input[id = usernameLogin]').type('admin')
    cy.get('input[id = passwordLogin]').type('admin')
    cy.get('input[id = loginButton]').click()
    cy.get('button[id = addListButton]').click()
    cy.get('input[id = name]').type('nowalista')
    cy.get('input[id = date]').type('2023-01-27')
    cy.get('input[id = addButton]').click()
    cy.get('button[id = addOrderButton]').eq(-1).click()
    cy.get('input[id = item]').type('Drukarka')
    cy.get('input[id = amount]').type('1')
    cy.get('select[id = grammageSelect]').select(0);
    cy.get('input[id = add]').click()
  })

  it('passes', () => {
    cy.visit('http://localhost:4200/')
    cy.get('input[id = usernameLogin]').type('admin')
    cy.get('input[id = passwordLogin]').type('admin')
    cy.get('input[id = loginButton]').click()
    cy.get('td[id = orderClick]').eq(-1).click()
  })


  it('passes', () => {
    cy.visit('http://localhost:4200/')
    cy.get('input[id = usernameLogin]').type('admin')
    cy.get('input[id = passwordLogin]').type('admin')
    cy.get('input[id = loginButton]').click()
    cy.get('button[id = editButton]').eq(-1).click()
    cy.get('input[id = name]').type('zmienionalista')
    cy.get('input[id = date]').type('2023-02-04')
    cy.get('input[id = update]').click()
  })

  it('passes', () => {
    cy.visit('http://localhost:4200/')
    cy.get('input[id = usernameLogin]').type('admin')
    cy.get('input[id = passwordLogin]').type('admin')
    cy.get('input[id = loginButton]').click()
    cy.get('button[id = deleteButton]').eq(-1).click()
  })
})
