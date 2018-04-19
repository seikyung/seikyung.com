var express = require('express');
var router = express.Router();

// Connect to process.env.DATABASE_URL when your app initializes:
// Read only reference value (const)
// get only Client class from pg package
const Client = require('pg').Client;
// create an instance from Client
const client = new Client({
  connectionString: process.env.DATABASE_URL,
});

// connect to the DATABASE_URL
client.connect();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

/* GET home page. */
router.get('/books', function(req, response, next) {
  // client object enables issuing SQL queries
  client.query('SELECT * FROM book', function(err, result){
    if (err) {
      next(err);
    }
    response.render('books',result);
  });
});

module.exports = router;