// export function for listening to the socket
module.exports = function (socket) {

  socket.on('change:graph', function (data) {
    console.log(data);
    socket.broadcast.emit('change:graph', data);
  });

};
