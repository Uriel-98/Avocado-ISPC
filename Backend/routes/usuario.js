const express = require('express')
const router = express.Router()
const db = require('../conection')

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