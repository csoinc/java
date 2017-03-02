var express = require('express');
var fs = require("fs");

var bodyParser = require('body-parser');
var multer  = require('multer');
var cookieParser = require('cookie-parser')

var app = express();

app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended: false }));
//app.use(multer({ dest: './tmp/'}));
app.use(multer({dest:'./uploads/'}).single('file'));
//app.use(multer({dest:'./uploads/'}).any());
app.use(cookieParser());


app.get('/index.html', function (req, res) {
	   res.sendFile( __dirname + "/" + "index.html" );
})

app.get('/upload.html', function (req, res) {
	   res.sendFile( __dirname + "/" + "upload.html" );
})

app.get('/process_get', function (req, res) {

   // Prepare output in JSON format
   response = {
       first_name:req.query.first_name,
       last_name:req.query.last_name
   };
   console.log(response);
   res.end(JSON.stringify(response));
})

// This responds with "Hello World" on the homepage
app.get('/', function (req, res) {
   console.log("Got a GET request for the homepage");
   res.send('Hello GET');var fs = require("fs");

   var bodyParser = require('body-parser');
   var multer  = require('multer');
   console.log("Cookies: ", req.cookies);
})

app.post('/file_upload', function (req, res) {
   console.log(req.file.name);
   console.log(req.file.path);
   console.log(req.file.type);

   var filename = "";
   if (!empty(req.file.name)) 
	   filename = req.file.name;
   else 
	   filename = "file1";
   var file = __dirname + "/" + filename;
   fs.readFile( req.file.path, function (err, data) {
        fs.writeFile(file, data, function (err) {
         if( err ){
              console.log( err );
         }else{
               response = {
                   message:'File uploaded successfully',
                   filename:req.file.name
              };
          }
          console.log( response );
          res.end( JSON.stringify( response ) );
       });
   });
})

// This responds a POST request for the homepage
app.post('/', function (req, res) {
   console.log("Got a POST request for the homepage");
   res.send('Hello POST');
})

// This responds a DELETE request for the /del_user page.
app.get('/del_user', function (req, res) {
   console.log("Got a DELETE request for /del_user");
   res.send('Hello DELETE');
})

// This responds a GET request for the /list_user page.
app.get('/list_user', function (req, res) {
   console.log("Got a GET request for /list_user");
   res.send('Page Listing');
})

// This responds a GET request for abcd, abxcd, ab123cd, and so on
app.get('/ab*cd', function(req, res) {   
   console.log("Got a GET request for /ab*cd");
   res.send('Page Pattern Match');
})


function empty(e) {
  switch (e) {
    case "":
    case 0:
    case "0":
    case null:
    case false:
    case typeof this == "undefined":
      return true;
    default:
      return false;
  }
}

var server = app.listen(8081, function () {

  var host = server.address().address
  var port = server.address().port

  console.log("Example app listening at http://%s:%s", host, port)

})
