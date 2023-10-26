const express = require('express')
const router = express.Router()
const bcrypt = require('bcrypt')
const db = require('../conection')
const { checkSchema, validationResult} = require('express-validator')
const validaciones = require('../utils/validacionesRegistro')

router.post('/', checkSchema(validaciones), (req, res) => {
  if(Object.keys(req.body).length === 0 || typeof req.body.password === 'undefined' || typeof req.body.email === 'undefined' || typeof req.body.nombreCompleto === 'undefined' || typeof req.body.usuario === 'undefined'){
    res.status(400).send('Bad request. Campos incorrectos')
    return
} 
const resValidaciones = validationResult(req).array()

if(resValidaciones.length > 0){
  res.send({
    success: false,
    message: "Error en registro. Campos inválidos",
    content: resValidaciones
  })
  console.log(req.body)
  return
}

// lógica de registro
const pass = bcrypt.hashSync(req.body.password, 12)

db.query(`CALL sp_registro('${req.body.email}','${req.body.nombreCompleto}','${req.body.usuario}', '${pass}');`, function (error, results, fields){
  const response = results[0][0]
  console.log(response)
  if(error){
    res.send({
      success: false,
      message: error
    })
    return
  }
  if(response.success == 0){
    res.send({
      success: false,
      message: response.message
    })
  } else {
    res.send({
      success: true,
      message: response.message
    })
  }
})
})

module.exports = router