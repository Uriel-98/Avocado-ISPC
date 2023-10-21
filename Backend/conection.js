const mysql2 = require('mysql2')

//agregar archivos .env para datos

const bd = mysql2.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'root',
  database: 'avocado'
})

module.exports = bd;