const mysql = require('mysql2');
const fs = require('fs');
require('dotenv').config();


// Make sure you have the db created on mysql
// Otherwise the script won't work
const connection = mysql.createConnection({
    host: process.env.HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PWD,
    database: process.env.DB_NAME
});

// connection.query("DROP TABLE users_widgets", function(err, res) {
//   if (err) {
//     console.log(err);
//   }
//
//   console.log(res);
// })

connection.query("DROP TABLE ", function(err, res) {
  if (err) {
    console.log(err);
  }
  console.log(res);
});



// let services;
//
// fs.readFile('./about.json', (err, jsonString) => {
//     if (err) {
//         console.log(err);
//         process.exit();
//     }
//
//     try {
//         services = JSON.parse(jsonString);
//     } catch (e) {
//         console.log(e);
//         process.exit();
//     }
//
//     services.forEach(service => {
//
//     })
//
// })
