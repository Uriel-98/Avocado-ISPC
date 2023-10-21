const express = require('express')
const router = express.Router()
const db = require('../conection')
const bcrypt = require('bcrypt')

router.post('/', (req, res) => {
 

if(Object.keys(req.body).length === 0 || typeof req.body.password === 'undefined' || typeof req.body.email === 'undefined'){
    res.status(400).send('Bad request. Campos incorrectos')
    return
}
const pass = req.body.password;
let resBody = {}

 db.query(`CALL sp_iniciarSesion('${req.body.email}');`, function(error, results, fields){
  const response = results[0][0]
    if(error){
      res.send(error)
    }
    else {
      if(response.success){
      bcrypt.compareSync(pass, response.result) ? resBody = {success: true, message: "Sesión iniciada"} : resBody = {success: false, message: "Contraseña incorrecta"}

      }
      else{
        resBody = {
          success: false,
          message: 'El usuario no existe'
        }
      }
      res.send(resBody)
    }
  }
 )
})

router.post('/hash', (req, res) => {
  console.log(req.body)
  const hash = bcrypt.hashSync(req.body.contraseña, 12)
  res.send({
    pass: req.body.contraseña,
    hash
  })
})



module.exports = router