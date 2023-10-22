const express = require('express')
const router = express.Router()
const db = require('../conection')

router.put('actualizarPerfil', (req, res) => {
  console.log(req.body)
  res.send('fin')
  // const nombreCompleto = req.body.nombreCompleto || null;
  // const usuario = req.body.usuario || null;
  // const imagen = req.body.imagen || null;
  // const email = req.body.email;
  // console.log(req.body)
  // if(!req.body.email){
  //   res.status(400).json('Email obligatorio')
  //   return
  // }

  // res.send({
  //   nombreCompleto,
  //   usuario,
  //   imagen,
  //   email
  // })

  // db.query(`CALL sp_actualizarPerfil('${email}', '${nombreCompleto}', '${imagen}', '${usuario}');`, function(error, results) {
  //   if(error){
  //     res.send({
  //       success: false,
  //       message: error
  //     })
  //   }
  //   else {
  //    res.send({
  //     success: true,
  //     message: 'Se actualizaron los datos correctamente'
  //    })
  //   }
  // })

})

router.delete('/eliminar', (req, res) => {
  const email = req.body.email
 console.log(email)
 if(typeof email === 'undefined'){
  res.status(400).json('No se encontró el email')
  return
 }
db.query(`DELETE FROM usuarios WHERE email = '${email}'`, function(error, results){
  if(error){
    res.send(error)
  }
  else{
    req.session.destroy(err => {
      if(err){
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