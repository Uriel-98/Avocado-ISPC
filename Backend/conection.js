const mysql2 = require('mysql2')
require('dotenv').config()

//agregar archivos .env para datos

const bd = mysql2.createConnection({
  host: 'localhost',
  user: process.env.DB_USER || 'root',
  password: process.env.DB_PASSWORD || '',
  database: process.env.DB_NAME || 'avocado',
  port: process.env.DB_PORT || 3306
})

module.exports = bd;