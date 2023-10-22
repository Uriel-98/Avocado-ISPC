const express = require('express')
const router = express.Router()
const db = require('../conection')
const { checkSchema, validationResult } = require('express-validator')
const validaciones = require('../utils/validacionesPerfil')

router.put('/actualizarPerfil', checkSchema(validaciones), (req, res) => {
  const resValidaciones = validationResult(req).array()
  const nombreCompleto = req.body.nombreCompleto || null;
  const usuario = req.body.usuario || null;
  const imagen = req.body.imagen || null;
  const email = req.body.email;
  if (!req.body.email) {
    res.status(400).json('Email obligatorio')
    return
  }

  if (resValidaciones.length > 0) {
    res.send({
      success: false,
      message: 'Campos inválidos',
      content: resValidaciones
    })
    return
  }

  db.query(`CALL sp_actualizarPerfil('${email}', '${nombreCompleto}', '${imagen}', '${usuario}');`, function (error, results) {
    if (error) {
      res.send({
        success: false,
        message: error
      })
    }
    else {
      res.send({
        success: true,
        message: 'Se actualizaron los datos correctamente'
      })
    }
  })
return
})

router.delete('/eliminar', (req, res) => {
  const email = req.body.email
  console.log(email)
  if (typeof email === 'undefined') {
    res.status(400).json('No se encontró el email')
    return
  }
  db.query(`DELETE FROM usuarios WHERE email = '${email}'`, function (error, results) {
    if (error) {
      res.send(error)
    }
    else {
      req.session.destroy(err => {
        if (err) {
          res.send(err)
          return
        }
      })
      res.send({
        success: true,
        message: 'Usuario eliminado correctamente'
      })
    }
  })

})

module.exports = router