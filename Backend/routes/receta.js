const express = require('express')
const router = express.Router()
const db = require('../conection')

router.get('/getCategorias', (req, res)=> {
  db.query(`SELECT * FROM categorias;`,function(error, results){
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

router.get('/buscarReceta/:titulo', (req, res)=> {
const titulo = req.params.titulo
  db.query(`CALL sp_buscarReceta('${titulo}');`, function(error, results){
    if(error){
      res.send({
        success:false,
        message: error
      })
    } else {
      res.send({
        success:true,
        message: '',
        content: results[0]
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

router.post('/getRecetasUsuario', (req, res) => {
const email = req.body.email
if(!email){
  res.status(400).json('Error. Email obligatorio.')
  return
}

db.query(`CALL sp_getRecetasUsuario('${email}')`, function(error, results){
  if(error){
    res.send({
      success: false,
      message: error
    })
    return
  }
  else {
    res.send({
      success: true,
      message: '',
      content: results[0]
    })
  }
})
})

module.exports = router