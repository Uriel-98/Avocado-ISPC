const express = require('express')
const router = express.Router()
const db = require('../conection')
const bcrypt = require('bcrypt')
const { checkSchema, validationResult } = require('express-validator')
const validaciones = require('../utils/validacionesPerfil')
const validacionesPass = require('../utils/validacionesPassword')

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

router.put('/modificarPassword', checkSchema(validacionesPass), (req, res) =>{
  const resValidaciones = validationResult(req).array();
  const pass = req.body.password
  const nuevoPass = req.body.nuevoPassword
  const email = req.body.email

  if(!email || !pass || !nuevoPass){
    res.status(400).json('Error. Faltan campos obligatorios')
    return
  }
  if(resValidaciones.length > 0){
    res.send({
      success: false,
      message: 'Campos inválidos',
      result: resValidaciones
    })
    return
  }

  db.query(`SELECT contraseña FROM usuarios WHERE email = '${email}';`, function(error, results){
    if(error){
      res.send({
        success: false,
        message: error
      })
      return
    } else {
      const resultado = results[0]
      if(bcrypt.compareSync(pass, resultado.contraseña)){
        db.query(`UPDATE usuarios SET contraseña = '${bcrypt.hashSync(nuevoPass, 12)}' WHERE email = '${email}';`, function(error, results){
          if(error){
            res.send({
              success: false,
              message: error
            })
            return 
          } else {
            res.send({
              success: true,
              message: 'Contraseña actualizada correctamente'
            })
            return
          }
        })
      } else {
        res.send({
          success: false,
          message: 'La contraseña es incorrecta'
        })
      }
    }
  })

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