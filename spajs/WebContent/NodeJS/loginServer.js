var http = require('http');
var port = process.env.PORT || 8090;

http.createServer(function(req, res) {
	res.writeHead(200, { 'Content-Type' : 'text/html' });
	res.end('You are logged in\n');
	res.loggedIn = true;
}).listen(port);

console.log('Server running on port %s', port);
