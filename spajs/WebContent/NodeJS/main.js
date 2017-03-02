console.log("call back start");

var fs = require("fs");

var data = fs.readFileSync('input.txt');

console.log(data.toString());

fs.readFile('input.txt', function(err, data) {
    if (err)
        return console.error(err);
    console.log("call back output data:" + data.toString());
});

console.log("call back end");

console.log("event start");

var events = require('events');
var eventEmitter = new events.EventEmitter();

// listener #1
var listner1 = function listner1() {
    console.log('listner1 executed.');
}

// listener #2
var listner2 = function listner2() {
    console.log('listner2 executed.');
}

// Bind the connection event with the listner1 function
eventEmitter.addListener('connection', listner1);

// Bind the connection event with the listner2 function
eventEmitter.on('connection', listner2);

var eventListeners = require('events').EventEmitter.listenerCount(eventEmitter, 'connection');
console.log(eventListeners + " Listner(s) listening to connection event");

// Fire the connection event
eventEmitter.emit('connection');

// Remove the binding of listner1 function
eventEmitter.removeListener('connection', listner1);
console.log("Listner1 will not listen now.");

// Fire the connection event
eventEmitter.emit('connection');

eventListeners = require('events').EventEmitter.listenerCount(eventEmitter, 'connection');
console.log(eventListeners + " Listner(s) listening to connection event");

console.log("event end");

console.log("buffer start");

buf = new Buffer(26);
for (var i = 0; i < 26; i++) {
    buf[i] = i + 97;
}

console.log(buf.toString('ascii')); // outputs: abcdefghijklmnopqrstuvwxyz
console.log(buf.toString('ascii', 0, 5)); // outputs: abcde
console.log(buf.toString('utf8', 0, 5)); // outputs: abcde
console.log(buf.toString(undefined, 0, 5)); // encoding defaults to 'utf8',
// outputs abcde

console.log("buffer end");

console.log("buffer to json start");
var buf = new Buffer('Simply Easy Learning');
var json = buf.toJSON(buf);

console.log(json);

console.log("buffer to json end");

console.log("buffer compare start");
var buffer1 = new Buffer('ABC');
var buffer2 = new Buffer('ABCD');
var result = buffer1.compare(buffer2);

if (result < 0) {
    console.log(buffer1 + " comes before " + buffer2);
} else if (result == 0) {
    console.log(buffer1 + " is same as " + buffer2);
} else {
    console.log(buffer1 + " comes after " + buffer2);
}

console.log("buffer compare end");

console.log("buffer copy start");
var buffer1 = new Buffer('ABC');
// copy a buffer
var buffer2 = new Buffer(3);
buffer1.copy(buffer2);
console.log("buffer2 content: " + buffer2.toString());
console.log("buffer copy end");

console.log("buffer slice start");
var buffer1 = new Buffer('TutorialsPoint');
// slicing a buffer
var buffer2 = buffer1.slice(0, 9);
console.log("buffer2 content: " + buffer2.toString());
console.log("buffer slice end");

var buffer = new Buffer('TutorialsPoint');
// length of the buffer
console.log("buffer length: " + buffer.length);
