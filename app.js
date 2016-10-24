'use strict';

/**
 * Module dependencies.
 */

var express = require('express');
var http = require('http');

var socket = require('./routes/socket.js');

var app = express();
var server = http.createServer(app);
var PieChart = require("react-chartjs").Pie;

/* Configuration */
app.set('views', __dirname + '/views');
app.use(express.static(__dirname + '/public'));
app.set('port', 3000);
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: false}));

if (process.env.NODE_ENV === 'development') {
	app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
}

/* Socket.io Communication */
var io = require('socket.io').listen(server);
io.sockets.on('connection', socket);

/* Start server */
server.listen(app.get('port'), function (){
  console.log('Express server listening on port %d in %s mode', app.get('port'), app.get('env'));
});

app.post('/notes', function(req, res) {
  io.emit('change:graph',req.body.no); 
});

module.exports = app;
