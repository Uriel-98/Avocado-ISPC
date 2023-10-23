const express = require('express')
const router = express.Router()
const db = require('../conection')

router.get('/getRecetasFeed', (req, res) => {
  db.query(`SELECT  r.idReceta, r.titulo, u.usuario AS creadoPor, r.imagen, r.fechaCreacion, r.fechaActualizacion FROM recetas r INNER JOIN usuarios u ON u.idUsuario = r.creadoPor LIMIT 20;`, function(error, results){
    if(error){
      res.send({
        success:false,
        message: error
      })
    } else {
      res.send({
        success:true,
        message: '',
        content: results
      })
    }
  })
})

router.get('/getRecetaById/:id', (req, res)=>{
  const idReceta = req.params.id
  db.query(`CALL sp_getReceta(${idReceta});`, function (error, results){
    if(error){
      res.send({
        success:false,
        message: error
      })
    } 

    else {
      res.send(results[0][0])
      return
    }
    
  })

  
})

module.exports = router