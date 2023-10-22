const validaciones = {
  email: {
    notEmpty: {
      errorMessage: 'Campo obligatorio',
      bail: true
    },
    isEmail: {
      errorMessage: 'Email inválido'
    }
  },
  password: {
    notEmpty: {
      errorMessage: 'Campo obligatorio',
      bail: true
    }
  }
}

module.exports = validaciones;