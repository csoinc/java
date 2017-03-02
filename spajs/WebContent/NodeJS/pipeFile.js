var fs = require("fs");

// Create a readable stream
var readerStream = fs.createReadStream('input.txt');

// Create a writable stream
var writerStream = fs.createWriteStream('output.txt');

// Pipe the read and write operations
// read input.txt and write data to output.txt
readerStream.pipe(writerStream);

console.log("Program Ended");

var fs = require("fs");
var zlib = require('zlib');

// Compress the file input.txt to input.txt.gz
fs.createReadStream('input.txt').pipe(zlib.createGzip()).pipe(fs.createWriteStream('input.txt.gz'));

console.log("File Compressed.");

var fs = require("fs");
var zlib = require('zlib');

// Decompress the file input.txt.gz to input.txt
fs.createReadStream('input.txt.gz').pipe(zlib.createGunzip()).pipe(fs.createWriteStream('input.txt'));

console.log("File Decompressed.");

var fs = require("fs");

//Asynchronous read
fs.readFile('input.txt', function(err, data) {
	if (err) {
		return console.error(err);
	}
	console.log("Asynchronous read: " + data.toString());
});

//Synchronous read
var data = fs.readFileSync('input.txt');
console.log("Synchronous read: " + data.toString());

console.log("Program Ended");

var fs = require("fs");

//Asynchronous - Opening File
console.log("Going to open file!");
fs.open('input.txt', 'r+', function(err, fd) {
	if (err) {
		return console.error(err);
	}
	console.log("File opened successfully!");
});

var fs = require("fs");

console.log("Going to get file info!");
fs.stat('input.txt', function(err, stats) {
	if (err) {
		return console.error(err);
	}
	console.log(stats);
	console.log("Got file info successfully!");

	// Check file type
	console.log("isFile ? " + stats.isFile());
	console.log("isDirectory ? " + stats.isDirectory());
});

