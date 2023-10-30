const validaciones = {
  titulo: {
    notEmpty: {
      errorMessage: 'Campo obligatorio',
      bail: true
    },
    isLength: {
      options: {
        min: 5,
        max: 250,
      },
      errorMessage: 'El título debe tener entre 5 y 250 caracteres',
      bail: true
    }
  },
  email: {
    notEmpty: {
      errorMessage: 'Campo obligatorio',
      bail: true
    },
    isEmail: {
      errorMessage: 'Email inválido'
    }
  },
  descripcion: {
    notEmpty: {
      errorMessage: 'Campo obligatorio',
      bail: true
    } 
  },
  imagen: {
    notEmpty: {
      errorMessage: 'Campo obligatorio',
      bail: true
    }
  },
  pasos: {
    notEmpty: {
      errorMessage: 'Campo obligatorio',
      bail: true
    },
    isArray: {
      errorMessage: 'Tipo de dato inválido. Debe ser array',
      bail: true
    }
  },
  tiempoCoccion: {
    optional: true,
    isLength: {
      options: {
        min: 2,
        max: 20
      },
      errorMessage: 'El campo debe tener entre 2 y 20 caracteres'
    }
  },
  dificultad: {
    optional: true,
    isLength: {
      options: {
        min: 2,
        max: 15
      },
      errorMessage: 'El campo debe tener entre 2 y 15 caracteres'
    }
  },
  categorias: {
    optional: true,
    isArray: {
      errorMessage: 'Tipo de dato inválido. Debe ser array'
    }
  },
  ingredientes: {
    notEmpty: {
      errorMessage: 'Campo obligatorio',
      bail: true
    },
    isArray: {
      errorMessage: 'Tipo de dato inválido. Debe ser array',
      bail: true
    }
  }
}

module.exports = validaciones;