const express = require('express')
const router = express.Router()
const db = require('../conection')

router.get('/', (req, res) => {
 db.query('SELECT * FROM usuarios WHERE idUsuario = 3;', function(error, results, fields){
    if(error){
      res.send(error)
    }
    else {
      res.send(results[0])
    }
  }
 )
})

module.exports = router