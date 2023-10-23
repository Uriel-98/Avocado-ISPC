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
  nombreCompleto: {
    optional: true,
    matches: {
      options: /^[A-Za-z\s]+$/,
      errorMessage: 'El nombre no debe contener números ni caracteres especiales'
    }
  },
  usuario: {
    optional: true,
    isLength: {
      options: {
        min: 5,
        max: 15,
      },
      errorMessage: 'El nombre de usuario debe tener entre 5 y 15 caracteres',
      bail: true
    },
    matches: {
      options: /^\S+$/,
      errorMessage: 'El nombre de usuario no debe contener espacios',
      bail: true
    } 
  }
}

module.exports = validaciones;