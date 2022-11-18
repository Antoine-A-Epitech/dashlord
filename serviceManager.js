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

let widgetServices = {
    services: [],
};



connection.query("SELECT * FROM widgetservices",async function(err, res) {
    if (err) {
        console.log("Error while fetching the widget services :" ,err);
    }


    for(const widgetService of res) {
        await new Promise((resolve, reject) => {
            connection.query("SELECT * FROM widgets WHERE service_id = ?", [widgetService.id], function(err, widgets){
                if (err) {
                    reject(err);
                }
                widgetService.widgets = widgets;
                resolve(widgetService);
            })
        });

        widgetServices.services.push(widgetService);
    }

    let jsonObject = JSON.stringify(widgetServices);

    fs.writeFile("./about.json", jsonObject, "utf8", function(err) {
        if (err) {
            console.log(err);
        }

        console.log("The about.json was created !");

        process.exit();
    })
});